package org.example.ems;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/**
 * The type Sensor latest job.
 */
public class SensorLatestJob {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // Source 설정
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
            .setBootstrapServers("kafka:9092")  // 부트스트랩서버 설정 -> 도커 컨테이너 도메인
            .setTopics("raw.sensor.energy")     // 토픽
            .setGroupId("ems-sensor-latest-job")    // Consumer GroupId 설정
            .setStartingOffsets(OffsetsInitializer.earliest())  // Kafka메시지를 어디서부터 읽을건지 결정 earliest는 가장 오래된 메세지 부터 읽음
            .setValueOnlyDeserializer(new SimpleStringSchema())
            .build();

        env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")
                .map(SensorLatestJob::parseJson)
                .filter(Objects::nonNull)
                .returns(SensorEvent.class) // 타입 힌트
                .addSink(
                        JdbcSink.sink(
                                """
                                        INSERT INTO sensor_latest
                                        (
                                            device_id,
                                            site_id,
                                            zone_id,
                                            power_usage,
                                            status,
                                            event_time,
                                            ingestion_time,
                                            updated_at
                                        )
                                        VALUES(?, ?, ?, ?, ?, ?, ?, now())
                                        ON CONFLICT (device_id)
                                        DO UPDATE SET
                                            site_id = EXCLUDED.site_id,
                                            zone_id = EXCLUDED.zone_id,
                                            power_usage = EXCLUDED.power_usage,
                                            status = EXCLUDED.status,
                                            event_time = EXCLUDED.event_time,
                                            ingestion_time = EXCLUDED.ingestion_time,
                                            updated_at = CURRENT_TIMESTAMP
                                        """,
                                (ps, event) -> {
                                    ps.setString(1, event.getDeviceId());
                                    ps.setString(2, event.getSiteId());
                                    ps.setString(3, event.getZoneId());
                                    ps.setDouble(4, event.getPowerUsage());
                                    ps.setString(5, event.getStatus());
                                    ps.setTimestamp(6, Timestamp.from(event.getEventTime()));
                                    ps.setTimestamp(7, Timestamp.from(event.getIngestionTime()));
                                },
                                JdbcExecutionOptions.builder()
                                        .withBatchSize(100)
                                        .withBatchIntervalMs(1000)
                                        .withMaxRetries(3)
                                        .build(),
                                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                        .withUrl("jdbc:postgresql://postgres:5432/ems")
                                        .withDriverName("org.postgresql.Driver")
                                        .withUsername(EnvUtils.POSTGRES_USERNAME)
                                        .withPassword(EnvUtils.POSTGRES_PASSWORD)
                                        .build()
                        )
                );

        // Job 시작
        env.execute("EMS Sensor Latest Job");
    }

    /**
     * json Parse함수
     * @param raw
     * @return
     */
    private static SensorEvent parseJson(String raw) {
        try {
            JsonNode jsonNode = objectMapper.readTree(raw);
            return new SensorEvent(
                    jsonNode.get("device_id").asText(),
                    jsonNode.get("site_id").asText(),
                    jsonNode.get("zone_id").asText(),
                    jsonNode.get("power_usage").asDouble(),
                    jsonNode.get("status").asText(),
                    Instant.parse(jsonNode.get("event_time").asText()),
                    Instant.parse(jsonNode.get("ingestion_time").asText())
            );
        } catch (Exception e) {
            System.out.println("Failed to parse event: " + raw);
            return null;
        }
    }
}
