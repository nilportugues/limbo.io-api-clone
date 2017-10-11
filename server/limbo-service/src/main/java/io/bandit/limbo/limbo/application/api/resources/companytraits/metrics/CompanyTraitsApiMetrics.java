package io.bandit.limbo.limbo.application.api.resources.companytraits.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class CompanyTraitsApiMetrics extends Metrics {

    private static final String COMPANYTRAITS = ".companytraits";
    private static final String COMPANYTRAITS_TALENT = ".companytraits_talent";

    private static Counter companyTraitsErrorCounter;
    private static Counter companyTraitsSuccessCounter;
    private static Counter companyTraitsNotFoundCounter;
    private static Counter companyTraitsCreatedCounter;
    private static Counter companyTraitsUpdatedCounter;
    private static Counter companyTraitsDeletedCounter;

    private static Counter companyTraitsTalentErrorCounter;
    private static Counter companyTraitsTalentSuccessCounter;
    private static Counter companyTraitsTalentNotFoundCounter;
    private static Counter companyTraitsTalentCreatedCounter;
    private static Counter companyTraitsTalentUpdatedCounter;
    private static Counter companyTraitsTalentDeletedCounter;

    public CompanyTraitsApiMetrics(final MeterRegistry registry) {
        companyTraitsErrorCounter = registry.counter(API_ERROR + COMPANYTRAITS);
        companyTraitsSuccessCounter = registry.counter(API_FETCH + COMPANYTRAITS);
        companyTraitsNotFoundCounter = registry.counter(API_NOTFOUND + COMPANYTRAITS);
        companyTraitsCreatedCounter = registry.counter(API_CREATED + COMPANYTRAITS);
        companyTraitsUpdatedCounter = registry.counter(API_UPDATED + COMPANYTRAITS);
        companyTraitsDeletedCounter = registry.counter(API_DELETED + COMPANYTRAITS);

        companyTraitsTalentErrorCounter= registry.counter(API_ERROR + COMPANYTRAITS_TALENT);
        companyTraitsTalentSuccessCounter = registry.counter(API_FETCH + COMPANYTRAITS_TALENT);
        companyTraitsTalentCreatedCounter = registry.counter(API_CREATED + COMPANYTRAITS_TALENT);
        companyTraitsTalentUpdatedCounter = registry.counter(API_UPDATED + COMPANYTRAITS_TALENT);
        companyTraitsTalentDeletedCounter = registry.counter(API_DELETED + COMPANYTRAITS_TALENT);
        companyTraitsTalentNotFoundCounter = registry.counter(API_NOTFOUND + COMPANYTRAITS_TALENT);

    }

    public void incrementCompanyTraitsError() {
        increment(companyTraitsErrorCounter);
    }

    public void incrementCompanyTraitsSuccess() {
        increment(companyTraitsSuccessCounter);
    }

    public void incrementCompanyTraitsNotFound() {
        increment(companyTraitsNotFoundCounter);
    }

    public void incrementCompanyTraitsCreated() {
        increment(companyTraitsCreatedCounter);
    }

    public void incrementCompanyTraitsUpdated() {
        increment(companyTraitsUpdatedCounter);
    }

    public void incrementCompanyTraitsDeleted() {
        increment(companyTraitsDeletedCounter);
    }

    public void incrementCompanyTraitsTalentError() {
        increment(companyTraitsTalentErrorCounter);
    }

    public void incrementCompanyTraitsTalentSuccess() {
        increment(companyTraitsTalentSuccessCounter);
    }

    public void incrementCompanyTraitsTalentNotFound() {
        increment(companyTraitsTalentNotFoundCounter);
    }

    public void incrementCompanyTraitsTalentCreated() {
        increment(companyTraitsTalentCreatedCounter);
    }

    public void incrementCompanyTraitsTalentUpdated() {
        increment(companyTraitsTalentUpdatedCounter);
    }

    public void incrementCompanyTraitsTalentDeleted() {
        increment(companyTraitsTalentDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
