package org.example.ems;


import java.time.Instant;

/**
 * The type Sensor event.
 */
public class SensorEvent {
    private String deviceId;
    private String siteId;
    private String zoneId;
    private double powerUsage;
    private String status;
    private Instant eventTime;
    private Instant ingestionTime;

    /**
     * Instantiates a new Sensor event.
     */
    public SensorEvent() {
    }

    /**
     * Instantiates a new Sensor event.
     *
     * @param deviceId      the device id
     * @param siteId        the site id
     * @param zoneId        the zone id
     * @param powerUsage    the power usage
     * @param status        the status
     * @param eventTime     the event time
     * @param ingestionTime the ingestion time
     */
    public SensorEvent(String deviceId, String siteId, String zoneId, double powerUsage, String status, Instant eventTime, Instant ingestionTime) {
        this.deviceId = deviceId;
        this.siteId = siteId;
        this.zoneId = zoneId;
        this.powerUsage = powerUsage;
        this.status = status;
        this.eventTime = eventTime;
        this.ingestionTime = ingestionTime;
    }

    /**
     * Gets device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets device id.
     *
     * @param deviceId the device id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Gets site id.
     *
     * @return the site id
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * Sets site id.
     *
     * @param siteId the site id
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * Gets zone id.
     *
     * @return the zone id
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * Sets zone id.
     *
     * @param zoneId the zone id
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * Gets power usage.
     *
     * @return the power usage
     */
    public double getPowerUsage() {
        return powerUsage;
    }

    /**
     * Sets power usage.
     *
     * @param powerUsage the power usage
     */
    public void setPowerUsage(double powerUsage) {
        this.powerUsage = powerUsage;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets event time.
     *
     * @return the event time
     */
    public Instant getEventTime() {
        return eventTime;
    }

    /**
     * Sets event time.
     *
     * @param eventTime the event time
     */
    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Gets ingestion time.
     *
     * @return the ingestion time
     */
    public Instant getIngestionTime() {
        return ingestionTime;
    }

    /**
     * Sets ingestion time.
     *
     * @param ingestionTime the ingestion time
     */
    public void setIngestionTime(Instant ingestionTime) {
        this.ingestionTime = ingestionTime;
    }

    /**
     * To string string.
     *
     * @return the string
     */
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
