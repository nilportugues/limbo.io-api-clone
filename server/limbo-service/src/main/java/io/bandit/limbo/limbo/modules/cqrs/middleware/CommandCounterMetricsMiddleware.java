package io.bandit.limbo.limbo.modules.cqrs.middleware;


import io.bandit.limbo.limbo.infrastructure.cqrs.middlewares.IMiddleware;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.CompletableFuture;

public class CommandCounterMetricsMiddleware implements IMiddleware {

    private final Counter successCounter;
    private final Counter errorCounter;

    public CommandCounterMetricsMiddleware(final MeterRegistry registry) {
        this.successCounter = registry.counter("message_bus", "command_bus", "command_success");
        this.errorCounter = registry.counter("message_bus", "command_bus", "command_error");
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
