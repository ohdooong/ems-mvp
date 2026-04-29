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

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Instant getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(Instant windowStart) {
        this.windowStart = windowStart;
    }

    public Instant getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(Instant windowEnd) {
        this.windowEnd = windowEnd;
    }

    public Float getAvgPowerUsage() {
        return avgPowerUsage;
    }

    public void setAvgPowerUsage(Float avgPowerUsage) {
        this.avgPowerUsage = avgPowerUsage;
    }

    public Float getTotalPowerUsage() {
        return totalPowerUsage;
    }

    public void setTotalPowerUsage(Float totalPowerUsage) {
        this.totalPowerUsage = totalPowerUsage;
    }

    public Long getEventCount() {
        return eventCount;
    }

    public void setEventCount(Long eventCount) {
        this.eventCount = eventCount;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
