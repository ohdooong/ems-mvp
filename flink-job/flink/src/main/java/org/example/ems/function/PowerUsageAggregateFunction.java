package org.example.ems.function;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.example.ems.event.SensorEvent;
import org.example.ems.model.PowerUsageAccumulator;

public class PowerUsageAggregateFunction implements AggregateFunction<SensorEvent, PowerUsageAccumulator, PowerUsageAccumulator> {




    @Override
    public PowerUsageAccumulator createAccumulator() {
        return new PowerUsageAccumulator();
    }

    @Override
    public PowerUsageAccumulator add(SensorEvent value, PowerUsageAccumulator accumulator) {
        accumulator.add(value.getPowerUsage());
        return accumulator;
    }

    @Override
    public PowerUsageAccumulator getResult(PowerUsageAccumulator accumulator) {
        return accumulator;
    }

    @Override
    public PowerUsageAccumulator merge(PowerUsageAccumulator a, PowerUsageAccumulator b) {
        a.merge(b);
        return a;
    }
}
