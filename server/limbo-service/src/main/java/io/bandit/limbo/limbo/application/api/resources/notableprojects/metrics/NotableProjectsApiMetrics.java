package io.bandit.limbo.limbo.application.api.resources.notableprojects.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class NotableProjectsApiMetrics extends Metrics {

    private static final String NOTABLEPROJECTS = ".notableprojects";
    private static final String NOTABLEPROJECTS_TALENT = ".notableprojects_talent";

    private static Counter notableProjectsErrorCounter;
    private static Counter notableProjectsSuccessCounter;
    private static Counter notableProjectsNotFoundCounter;
    private static Counter notableProjectsCreatedCounter;
    private static Counter notableProjectsUpdatedCounter;
    private static Counter notableProjectsDeletedCounter;

    private static Counter notableProjectsTalentErrorCounter;
    private static Counter notableProjectsTalentSuccessCounter;
    private static Counter notableProjectsTalentNotFoundCounter;
    private static Counter notableProjectsTalentCreatedCounter;
    private static Counter notableProjectsTalentUpdatedCounter;
    private static Counter notableProjectsTalentDeletedCounter;

    public NotableProjectsApiMetrics(final MeterRegistry registry) {
        notableProjectsErrorCounter = registry.counter(API_ERROR + NOTABLEPROJECTS);
        notableProjectsSuccessCounter = registry.counter(API_FETCH + NOTABLEPROJECTS);
        notableProjectsNotFoundCounter = registry.counter(API_NOTFOUND + NOTABLEPROJECTS);
        notableProjectsCreatedCounter = registry.counter(API_CREATED + NOTABLEPROJECTS);
        notableProjectsUpdatedCounter = registry.counter(API_UPDATED + NOTABLEPROJECTS);
        notableProjectsDeletedCounter = registry.counter(API_DELETED + NOTABLEPROJECTS);

        notableProjectsTalentErrorCounter= registry.counter(API_ERROR + NOTABLEPROJECTS_TALENT);
        notableProjectsTalentSuccessCounter = registry.counter(API_FETCH + NOTABLEPROJECTS_TALENT);
        notableProjectsTalentCreatedCounter = registry.counter(API_CREATED + NOTABLEPROJECTS_TALENT);
        notableProjectsTalentUpdatedCounter = registry.counter(API_UPDATED + NOTABLEPROJECTS_TALENT);
        notableProjectsTalentDeletedCounter = registry.counter(API_DELETED + NOTABLEPROJECTS_TALENT);
        notableProjectsTalentNotFoundCounter = registry.counter(API_NOTFOUND + NOTABLEPROJECTS_TALENT);

    }

    public void incrementNotableProjectsError() {
        increment(notableProjectsErrorCounter);
    }

    public void incrementNotableProjectsSuccess() {
        increment(notableProjectsSuccessCounter);
    }

    public void incrementNotableProjectsNotFound() {
        increment(notableProjectsNotFoundCounter);
    }

    public void incrementNotableProjectsCreated() {
        increment(notableProjectsCreatedCounter);
    }

    public void incrementNotableProjectsUpdated() {
        increment(notableProjectsUpdatedCounter);
    }

    public void incrementNotableProjectsDeleted() {
        increment(notableProjectsDeletedCounter);
    }

    public void incrementNotableProjectsTalentError() {
        increment(notableProjectsTalentErrorCounter);
    }

    public void incrementNotableProjectsTalentSuccess() {
        increment(notableProjectsTalentSuccessCounter);
    }

    public void incrementNotableProjectsTalentNotFound() {
        increment(notableProjectsTalentNotFoundCounter);
    }

    public void incrementNotableProjectsTalentCreated() {
        increment(notableProjectsTalentCreatedCounter);
    }

    public void incrementNotableProjectsTalentUpdated() {
        increment(notableProjectsTalentUpdatedCounter);
    }

    public void incrementNotableProjectsTalentDeleted() {
        increment(notableProjectsTalentDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
