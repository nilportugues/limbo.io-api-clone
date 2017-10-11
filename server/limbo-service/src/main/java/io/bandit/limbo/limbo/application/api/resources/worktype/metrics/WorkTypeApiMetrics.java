package io.bandit.limbo.limbo.application.api.resources.worktype.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class WorkTypeApiMetrics extends Metrics {

    private static final String WORKTYPE = ".worktype";

    private static Counter workTypeErrorCounter;
    private static Counter workTypeSuccessCounter;
    private static Counter workTypeNotFoundCounter;
    private static Counter workTypeCreatedCounter;
    private static Counter workTypeUpdatedCounter;
    private static Counter workTypeDeletedCounter;

    public WorkTypeApiMetrics(final MeterRegistry registry) {
        workTypeErrorCounter = registry.counter(API_ERROR + WORKTYPE);
        workTypeSuccessCounter = registry.counter(API_FETCH + WORKTYPE);
        workTypeNotFoundCounter = registry.counter(API_NOTFOUND + WORKTYPE);
        workTypeCreatedCounter = registry.counter(API_CREATED + WORKTYPE);
        workTypeUpdatedCounter = registry.counter(API_UPDATED + WORKTYPE);
        workTypeDeletedCounter = registry.counter(API_DELETED + WORKTYPE);

    }

    public void incrementWorkTypeError() {
        increment(workTypeErrorCounter);
    }

    public void incrementWorkTypeSuccess() {
        increment(workTypeSuccessCounter);
    }

    public void incrementWorkTypeNotFound() {
        increment(workTypeNotFoundCounter);
    }

    public void incrementWorkTypeCreated() {
        increment(workTypeCreatedCounter);
    }

    public void incrementWorkTypeUpdated() {
        increment(workTypeUpdatedCounter);
    }

    public void incrementWorkTypeDeleted() {
        increment(workTypeDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
