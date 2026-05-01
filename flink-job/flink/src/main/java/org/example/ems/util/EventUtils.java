package org.example.ems.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.example.ems.event.SensorEvent;

public class EventUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SensorEvent parseJsonFromSensor(String raw) {
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
