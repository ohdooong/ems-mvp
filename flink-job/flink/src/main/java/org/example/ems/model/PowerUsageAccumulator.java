package org.example.ems.model;

/**
 * 윈도우 안에서 합계와 개수를 모으는 Accumulator
 *
 */
public class PowerUsageAccumulator {
    private double totalPowerUsage;
    private long eventCount;

    public PowerUsageAccumulator() {
        this.totalPowerUsage = 0.0;
        this.eventCount = 0L;
    }

    public double getTotalPowerUsage() {
        return totalPowerUsage;
    }

    public long getEventCount() {
        return eventCount;
    }

    public void add(double powerUsage) {
        this.totalPowerUsage += powerUsage;
        this.eventCount++;
    }

    public double getAvgPowerUsage() {
        if (eventCount == 0) {
            return 0.0;
        }
        return totalPowerUsage / eventCount;
    }

    public void merge(PowerUsageAccumulator other) {
        this.totalPowerUsage += other.totalPowerUsage;
        this.eventCount += other.eventCount;
    }

}
