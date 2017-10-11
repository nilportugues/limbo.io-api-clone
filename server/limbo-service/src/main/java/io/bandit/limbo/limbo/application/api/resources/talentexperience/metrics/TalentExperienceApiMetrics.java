package io.bandit.limbo.limbo.application.api.resources.talentexperience.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class TalentExperienceApiMetrics extends Metrics {

    private static final String TALENTEXPERIENCE = ".talentexperience";

    private static Counter talentExperienceErrorCounter;
    private static Counter talentExperienceSuccessCounter;
    private static Counter talentExperienceNotFoundCounter;
    private static Counter talentExperienceCreatedCounter;
    private static Counter talentExperienceUpdatedCounter;
    private static Counter talentExperienceDeletedCounter;

    public TalentExperienceApiMetrics(final MeterRegistry registry) {
        talentExperienceErrorCounter = registry.counter(API_ERROR + TALENTEXPERIENCE);
        talentExperienceSuccessCounter = registry.counter(API_FETCH + TALENTEXPERIENCE);
        talentExperienceNotFoundCounter = registry.counter(API_NOTFOUND + TALENTEXPERIENCE);
        talentExperienceCreatedCounter = registry.counter(API_CREATED + TALENTEXPERIENCE);
        talentExperienceUpdatedCounter = registry.counter(API_UPDATED + TALENTEXPERIENCE);
        talentExperienceDeletedCounter = registry.counter(API_DELETED + TALENTEXPERIENCE);

    }

    public void incrementTalentExperienceError() {
        increment(talentExperienceErrorCounter);
    }

    public void incrementTalentExperienceSuccess() {
        increment(talentExperienceSuccessCounter);
    }

    public void incrementTalentExperienceNotFound() {
        increment(talentExperienceNotFoundCounter);
    }

    public void incrementTalentExperienceCreated() {
        increment(talentExperienceCreatedCounter);
    }

    public void incrementTalentExperienceUpdated() {
        increment(talentExperienceUpdatedCounter);
    }

    public void incrementTalentExperienceDeleted() {
        increment(talentExperienceDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
