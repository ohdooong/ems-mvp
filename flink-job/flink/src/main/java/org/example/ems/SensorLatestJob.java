package org.example.ems;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

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
            .setBootstrapServers("kafka:9092")  // 부트스트랩서버 설정
            .setTopics("raw.sensor.energy")     // 토픽
            .setGroupId("ems-sensor-latest-job")    // GroupId 설정
            .setStartingOffsets(OffsetsInitializer.earliest())  //
            .setValueOnlyDeserializer(new SimpleStringSchema())
            .build();



    }
}
