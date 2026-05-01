package org.example.ems.jobs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.example.ems.event.SensorEvent;
import org.example.ems.function.EnergyAggregateWindowFunction;
import org.example.ems.function.PowerUsageAggregateFunction;
import org.example.ems.model.EnergyAgg1M;
import org.example.ems.util.EnvUtils;
import org.example.ems.util.EventUtils;

/**
 * site_id + zone_id + 1분 window 기준으로 아래값을 계산
 * 구역 별 1분 평균을 구하는 것이 목표!!
 *
 * 평균 전력 사용량
 * 총 전력 사용량
 * 이벤트 수
 * window 시작 시간
 * window 종료 시간
 *
 * accumulator, function, model 조립하여 Stream처리
 */
public class SensorEnergyAgg1mJob {
    public static void main(String[] args) throws Exception {
        // Flink 실행 환경
        // Flink Job의 시작점
        // 전체 파이프라인 관리
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // Kafka Source 생성
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("raw.sensor.energy")     // 토픽
                .setGroupId("ems-energy-agg-1m-job")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();


        // Stream처리
        DataStreamSource<String> rawStream = env.fromSource(kafkaSource,
            WatermarkStrategy.noWatermarks(), "Kafka Source");

        DataStream<SensorEvent> eventStream = rawStream
            .map(EventUtils::parseJsonFromSensor)
            .filter(event -> event != null)
            .returns(SensorEvent.class);

        DataStream<SensorEvent> eventTimeStream = eventStream.assignTimestampsAndWatermarks(
            WatermarkStrategy
                .<SensorEvent>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                .withTimestampAssigner(
                    (event, timestamp) -> event.getEventTime().toEpochMilli()
                )
        );

        DataStream<EnergyAgg1M> aggregateStream = eventTimeStream
            .keyBy(SensorEvent::getDataStreamKey)
            .window(TumblingEventTimeWindows.of(Duration.ofMinutes(1)))
            .aggregate(
                new PowerUsageAggregateFunction(), //계산
                new EnergyAggregateWindowFunction());// 마지막 집계

        aggregateStream.print("1m-aggregate");

        // Postgres Sink
        aggregateStream.addSink(
            JdbcSink.sink("""
                    INSERT INTO energy_agg_1m
                    (site_id, zone_id, window_start, window_end, avg_power_usage, total_power_usage, event_count, updated_at)
                    VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                    ON CONFLICT (site_id, zone_id, window_start, window_end)
                    DO UPDATE SET
                        avg_power_usage = EXCLUDED.avg_power_usage,
                        total_power_usage = EXCLUDED.total_power_usage,
                        event_count = EXCLUDED.event_count,
                        updated_at = CURRENT_TIMESTAMP
                    """,
                (ps, event) -> {
                    ps.setString(1, event.getSiteId());
                    ps.setString(2, event.getZoneId());
                    ps.setTimestamp(3, Timestamp.from(event.getWindowStart()));
                    ps.setTimestamp(4, Timestamp.from(event.getWindowEnd()));
                    ps.setDouble(5, event.getAvgPowerUsage());
                    ps.setDouble(6, event.getTotalPowerUsage());
                    ps.setLong(7, event.getEventCount());
                },
                JdbcExecutionOptions.builder()
                    .withBatchSize(100)
                    .withBatchIntervalMs(1000)
                    .withMaxRetries(3)
                    .build(),

                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                    .withUrl(EnvUtils.POSTGRES_URL)
                    .withDriverName("org.postgresql.Driver")
                    .withUsername(EnvUtils.POSTGRES_USERNAME)
                    .withPassword(EnvUtils.POSTGRES_PASSWORD)
                    .build()
            )
        );

        // execute
        env.execute("EMS Energy 1m Aggregation Job");
    }
}