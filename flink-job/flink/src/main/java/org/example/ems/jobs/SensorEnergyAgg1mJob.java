package org.example.ems.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.ems.event.SensorEvent;

import java.time.Duration;

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

//        env.fromSource(kafkaSource,
//                WatermarkStrategy.<SensorEvent>forBoundedOutOfOrderness(Duration.ofSeconds(5))
//                        .withTimestampAssigner((event, ts) -> event.getEventTime()),
//                )
    }
}