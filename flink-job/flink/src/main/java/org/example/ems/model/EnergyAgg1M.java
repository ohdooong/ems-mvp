package org.example.ems.model;

import java.time.Instant;
import java.time.LocalDateTime;

public class EnergyAgg1M {
    private String siteId;
    private String zoneId;
    private Instant windowStart;
    private Instant windowEnd;
    private Float avgPowerUsage;
    private Float totalPowerUsage;
    private Long eventCount;
    private Instant updatedAt;

    public String getSiteId() {
        return siteId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public Instant getWindowStart() {
        return windowStart;
    }

    public Instant getWindowEnd() {
        return windowEnd;
    }

    public Float getAvgPowerUsage() {
        return avgPowerUsage;
    }

    public Float getTotalPowerUsage() {
        return totalPowerUsage;
    }

    public Long getEventCount() {
        return eventCount;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "EnergyAgg1M{" +
                "siteId='" + siteId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", windowStart=" + windowStart +
                ", windowEnd=" + windowEnd +
                ", avgPowerUsage=" + avgPowerUsage +
                ", totalPowerUsage=" + totalPowerUsage +
                ", eventCount=" + eventCount +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
