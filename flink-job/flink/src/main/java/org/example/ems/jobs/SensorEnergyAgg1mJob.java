package org.example.ems.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.ems.event.SensorEvent;

import java.time.Duration;

/**
 * site_id + zone_id + 1분 window 기준으로 아래값을 계산
 *
 * 평균 전력 사용량
 * 총 전력 사용량
 * 이벤트 수
 * window 시작 시간
 * window 종료 시간
 */
public class SensorEnergyAgg1mJob {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // Kafka Source 생성
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("raw.sensor.energy")     // 토픽
                .setGroupId("ems-sensor-agg-1m-job")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        env.fromSource(kafkaSource,
                WatermarkStrategy
                        .<SensorEvent>forBoundedOutOfOrderness(Duration.ofSeconds(5))   // 최대 5초 늦게 들어오는 데이터까지 허용
                        .withTimestampAssigner((event, timestamp) -> event.getEventTime().toEpochMilli()),
                "Kafka Source"
        ) ;
    }
}