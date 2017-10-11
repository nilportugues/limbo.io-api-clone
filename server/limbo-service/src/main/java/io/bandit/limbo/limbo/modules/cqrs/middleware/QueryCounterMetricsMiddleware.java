package io.bandit.limbo.limbo.modules.cqrs.middleware;


import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.CompletableFuture;

public class QueryCounterMetricsMiddleware implements IMiddleware {

    private final Counter successCounter;
    private final Counter errorCounter;

    public QueryCounterMetricsMiddleware(final MeterRegistry registry) {
        this.successCounter = registry.counter("message_bus", "query_bus", "query_success");
        this.errorCounter = registry.counter("message_bus", "query_bus", "query_error");
    }

    @Override
    public CompletableFuture execute(Object object) {
        return CompletableFuture
                .runAsync(() -> {})
                .whenCompleteAsync((aVoid, throwable) -> {
                    if (null != throwable) {
                        errorCounter.increment();
                        return;
                    }
                    successCounter.increment();
                });
    }
}
