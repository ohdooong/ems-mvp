package org.example.ems.function;

import java.time.Instant;
import java.util.Iterator;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.example.ems.model.EnergyAgg1M;
import org.example.ems.model.PowerUsageAccumulator;

/**
 * ProcessWindowFunction은 window가 닫힐 때 실행
 * 계산을 담당하는 객체에 window metadata 붙이기
 *
 */
public class EnergyAggregateWindowFunction
        extends ProcessWindowFunction<
        PowerUsageAccumulator,
        EnergyAgg1M,
        String,
        TimeWindow
        > {


    @Override
    public void process(String key,
        Context context,
        Iterable<PowerUsageAccumulator> elements,
        Collector<EnergyAgg1M> out) throws Exception {

        String[] keyParts = key.split("\\|");
        if (keyParts.length < 2) {
            return;
        }
        String siteId = keyParts[0];
        String zoneId = keyParts[1];

        Iterator<PowerUsageAccumulator> iterator = elements.iterator();
        if (!iterator.hasNext()) {
            return;
        }

        // AggregateFunction과 같이 쓸 때는 결과가 보통 하나만 들어옴.
        PowerUsageAccumulator acc = iterator.next();

        Instant windowStart = Instant.ofEpochMilli(context.window().getStart());
        Instant windowEnd = Instant.ofEpochMilli(context.window().getEnd());

        EnergyAgg1M result = new EnergyAgg1M(
            siteId,
            zoneId,
            windowStart,
            windowEnd,
            acc.getAvgPowerUsage(),
            acc.getTotalPowerUsage(),
            acc.getEventCount());

        out.collect(result);

    }
}
