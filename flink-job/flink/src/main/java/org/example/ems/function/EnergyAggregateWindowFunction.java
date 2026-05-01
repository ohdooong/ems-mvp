package org.example.ems.function;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.example.ems.model.EnergyAgg1M;
import org.example.ems.model.PowerUsageAccumulator;

/**
 * ProcessWindowFunction은 window가 닫힐 때 실행
 */
public class EnergyAggregateWindowFunction
        extends ProcessWindowFunction<
        PowerUsageAccumulator,
        EnergyAgg1M,
        String,
        TimeWindow
        > {


    @Override
    public void process(String s, ProcessWindowFunction<PowerUsageAccumulator, EnergyAgg1M, String, TimeWindow>.Context context, Iterable<PowerUsageAccumulator> elements, Collector<EnergyAgg1M> out) throws Exception {

    }
}
