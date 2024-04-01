package com.gym.prometeusMetrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class Metrics {
    private final Counter requestCounter;
    private final Gauge memoryGauge;

    public Metrics(MeterRegistry registry) {
        this.requestCounter = Counter.builder("requests.counter")
            .description("Number of requests to the application")
            .register(registry);

        this.memoryGauge = Gauge.builder("memory.usage", Runtime.getRuntime(), Runtime::totalMemory)
            .description("Current memory usage")
            .baseUnit("bytes")
            .register(registry);
    }

    public void incrementRequestCounter() {
        this.requestCounter.increment();
    }
}
