package org.example.ems.model;

/**
 * 윈도우 안에서 합계와 개수를 모으는 Accumulator
 *
 */
public class PowerUsageAccumulator {
    private double totalPowerUsage;
    private long eventCount;

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

}
