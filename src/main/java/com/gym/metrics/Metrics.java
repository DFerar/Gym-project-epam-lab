package com.gym.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class Metrics {
    private final Counter requestCounter;

    /**
     * The Metrics constructor. Initializes a Counter and a Gauge with the provided MeterRegistry.
     *
     * @param registry The {@code MeterRegistry} to which the metrics will be bound.
     */
    public Metrics(MeterRegistry registry) {
        this.requestCounter = Counter.builder("requests.counter")
            .description("Number of requests to the application")
            .register(registry);
    }

    public void incrementRequestCounter() {
        this.requestCounter.increment();
    }
}
