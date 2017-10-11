package io.bandit.limbo.limbo.application.api.resources.joboffer.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class JobOfferApiMetrics extends Metrics {

    private static final String JOBOFFER = ".joboffer";
    private static final String JOBOFFER_TALENT = ".joboffer_talent";

    private static Counter jobOfferErrorCounter;
    private static Counter jobOfferSuccessCounter;
    private static Counter jobOfferNotFoundCounter;
    private static Counter jobOfferCreatedCounter;
    private static Counter jobOfferUpdatedCounter;
    private static Counter jobOfferDeletedCounter;

    private static Counter jobOfferTalentErrorCounter;
    private static Counter jobOfferTalentSuccessCounter;
    private static Counter jobOfferTalentNotFoundCounter;
    private static Counter jobOfferTalentCreatedCounter;
    private static Counter jobOfferTalentUpdatedCounter;
    private static Counter jobOfferTalentDeletedCounter;

    public JobOfferApiMetrics(final MeterRegistry registry) {
        jobOfferErrorCounter = registry.counter(API_ERROR + JOBOFFER);
        jobOfferSuccessCounter = registry.counter(API_FETCH + JOBOFFER);
        jobOfferNotFoundCounter = registry.counter(API_NOTFOUND + JOBOFFER);
        jobOfferCreatedCounter = registry.counter(API_CREATED + JOBOFFER);
        jobOfferUpdatedCounter = registry.counter(API_UPDATED + JOBOFFER);
        jobOfferDeletedCounter = registry.counter(API_DELETED + JOBOFFER);

        jobOfferTalentErrorCounter= registry.counter(API_ERROR + JOBOFFER_TALENT);
        jobOfferTalentSuccessCounter = registry.counter(API_FETCH + JOBOFFER_TALENT);
        jobOfferTalentCreatedCounter = registry.counter(API_CREATED + JOBOFFER_TALENT);
        jobOfferTalentUpdatedCounter = registry.counter(API_UPDATED + JOBOFFER_TALENT);
        jobOfferTalentDeletedCounter = registry.counter(API_DELETED + JOBOFFER_TALENT);
        jobOfferTalentNotFoundCounter = registry.counter(API_NOTFOUND + JOBOFFER_TALENT);

    }

    public void incrementJobOfferError() {
        increment(jobOfferErrorCounter);
    }

    public void incrementJobOfferSuccess() {
        increment(jobOfferSuccessCounter);
    }

    public void incrementJobOfferNotFound() {
        increment(jobOfferNotFoundCounter);
    }

    public void incrementJobOfferCreated() {
        increment(jobOfferCreatedCounter);
    }

    public void incrementJobOfferUpdated() {
        increment(jobOfferUpdatedCounter);
    }

    public void incrementJobOfferDeleted() {
        increment(jobOfferDeletedCounter);
    }

    public void incrementJobOfferTalentError() {
        increment(jobOfferTalentErrorCounter);
    }

    public void incrementJobOfferTalentSuccess() {
        increment(jobOfferTalentSuccessCounter);
    }

    public void incrementJobOfferTalentNotFound() {
        increment(jobOfferTalentNotFoundCounter);
    }

    public void incrementJobOfferTalentCreated() {
        increment(jobOfferTalentCreatedCounter);
    }

    public void incrementJobOfferTalentUpdated() {
        increment(jobOfferTalentUpdatedCounter);
    }

    public void incrementJobOfferTalentDeleted() {
        increment(jobOfferTalentDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
