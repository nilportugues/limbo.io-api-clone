package io.bandit.limbo.limbo.application.api.resources.personaltraits.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class PersonalTraitsApiMetrics extends Metrics {

    private static final String PERSONALTRAITS = ".personaltraits";
    private static final String PERSONALTRAITS_TALENT = ".personaltraits_talent";

    private static Counter personalTraitsErrorCounter;
    private static Counter personalTraitsSuccessCounter;
    private static Counter personalTraitsNotFoundCounter;
    private static Counter personalTraitsCreatedCounter;
    private static Counter personalTraitsUpdatedCounter;
    private static Counter personalTraitsDeletedCounter;

    private static Counter personalTraitsTalentErrorCounter;
    private static Counter personalTraitsTalentSuccessCounter;
    private static Counter personalTraitsTalentNotFoundCounter;
    private static Counter personalTraitsTalentCreatedCounter;
    private static Counter personalTraitsTalentUpdatedCounter;
    private static Counter personalTraitsTalentDeletedCounter;

    public PersonalTraitsApiMetrics(final MeterRegistry registry) {
        personalTraitsErrorCounter = registry.counter(API_ERROR + PERSONALTRAITS);
        personalTraitsSuccessCounter = registry.counter(API_FETCH + PERSONALTRAITS);
        personalTraitsNotFoundCounter = registry.counter(API_NOTFOUND + PERSONALTRAITS);
        personalTraitsCreatedCounter = registry.counter(API_CREATED + PERSONALTRAITS);
        personalTraitsUpdatedCounter = registry.counter(API_UPDATED + PERSONALTRAITS);
        personalTraitsDeletedCounter = registry.counter(API_DELETED + PERSONALTRAITS);

        personalTraitsTalentErrorCounter= registry.counter(API_ERROR + PERSONALTRAITS_TALENT);
        personalTraitsTalentSuccessCounter = registry.counter(API_FETCH + PERSONALTRAITS_TALENT);
        personalTraitsTalentCreatedCounter = registry.counter(API_CREATED + PERSONALTRAITS_TALENT);
        personalTraitsTalentUpdatedCounter = registry.counter(API_UPDATED + PERSONALTRAITS_TALENT);
        personalTraitsTalentDeletedCounter = registry.counter(API_DELETED + PERSONALTRAITS_TALENT);
        personalTraitsTalentNotFoundCounter = registry.counter(API_NOTFOUND + PERSONALTRAITS_TALENT);

    }

    public void incrementPersonalTraitsError() {
        increment(personalTraitsErrorCounter);
    }

    public void incrementPersonalTraitsSuccess() {
        increment(personalTraitsSuccessCounter);
    }

    public void incrementPersonalTraitsNotFound() {
        increment(personalTraitsNotFoundCounter);
    }

    public void incrementPersonalTraitsCreated() {
        increment(personalTraitsCreatedCounter);
    }

    public void incrementPersonalTraitsUpdated() {
        increment(personalTraitsUpdatedCounter);
    }

    public void incrementPersonalTraitsDeleted() {
        increment(personalTraitsDeletedCounter);
    }

    public void incrementPersonalTraitsTalentError() {
        increment(personalTraitsTalentErrorCounter);
    }

    public void incrementPersonalTraitsTalentSuccess() {
        increment(personalTraitsTalentSuccessCounter);
    }

    public void incrementPersonalTraitsTalentNotFound() {
        increment(personalTraitsTalentNotFoundCounter);
    }

    public void incrementPersonalTraitsTalentCreated() {
        increment(personalTraitsTalentCreatedCounter);
    }

    public void incrementPersonalTraitsTalentUpdated() {
        increment(personalTraitsTalentUpdatedCounter);
    }

    public void incrementPersonalTraitsTalentDeleted() {
        increment(personalTraitsTalentDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
