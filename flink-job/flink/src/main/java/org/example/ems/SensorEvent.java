package org.example.ems;


import java.time.Instant;

public class SensorEvent {
    private String deviceId;
    private String siteId;
    private String zoneId;
    private double powerUsage;
    private String status;
    private Instant eventTime;
    private Instant ingestionTime;

    public SensorEvent() {
    }

    public SensorEvent(String deviceId, String siteId, String zoneId, double powerUsage, String status, Instant eventTime, Instant ingestionTime) {
        this.deviceId = deviceId;
        this.siteId = siteId;
        this.zoneId = zoneId;
        this.powerUsage = powerUsage;
        this.status = status;
        this.eventTime = eventTime;
        this.ingestionTime = ingestionTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public double getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(double powerUsage) {
        this.powerUsage = powerUsage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public Instant getIngestionTime() {
        return ingestionTime;
    }

    public void setIngestionTime(Instant ingestionTime) {
        this.ingestionTime = ingestionTime;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "deviceId='" + deviceId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", powerUsage=" + powerUsage +
                ", status='" + status + '\'' +
                ", eventTime=" + eventTime +
                ", ingestionTime=" + ingestionTime +
                '}';
    }
}
