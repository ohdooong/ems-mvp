package org.example.ems.model;

import java.time.Instant;

public class EnergyAgg1M {
    private String siteId;
    private String zoneId;
    private Instant windowStart;
    private Instant windowEnd;
    private double avgPowerUsage;
    private double totalPowerUsage;
    private long eventCount;
    private Instant updatedAt;

    public EnergyAgg1M(String siteId, String zoneId, Instant windowStart, Instant windowEnd,
        double avgPowerUsage, double totalPowerUsage, Long eventCount) {
        this.siteId = siteId;
        this.zoneId = zoneId;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.avgPowerUsage = avgPowerUsage;
        this.totalPowerUsage = totalPowerUsage;
        this.eventCount = eventCount;
    }

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

    public double getAvgPowerUsage() {
        return avgPowerUsage;
    }

    public double getTotalPowerUsage() {
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
