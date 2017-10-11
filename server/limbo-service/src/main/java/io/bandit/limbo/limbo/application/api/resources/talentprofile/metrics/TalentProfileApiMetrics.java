package io.bandit.limbo.limbo.application.api.resources.talentprofile.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class TalentProfileApiMetrics extends Metrics {

    private static final String TALENTPROFILE = ".talentprofile";

    private static Counter talentProfileErrorCounter;
    private static Counter talentProfileSuccessCounter;
    private static Counter talentProfileNotFoundCounter;
    private static Counter talentProfileCreatedCounter;
    private static Counter talentProfileUpdatedCounter;
    private static Counter talentProfileDeletedCounter;

    public TalentProfileApiMetrics(final MeterRegistry registry) {
        talentProfileErrorCounter = registry.counter(API_ERROR + TALENTPROFILE);
        talentProfileSuccessCounter = registry.counter(API_FETCH + TALENTPROFILE);
        talentProfileNotFoundCounter = registry.counter(API_NOTFOUND + TALENTPROFILE);
        talentProfileCreatedCounter = registry.counter(API_CREATED + TALENTPROFILE);
        talentProfileUpdatedCounter = registry.counter(API_UPDATED + TALENTPROFILE);
        talentProfileDeletedCounter = registry.counter(API_DELETED + TALENTPROFILE);

    }

    public void incrementTalentProfileError() {
        increment(talentProfileErrorCounter);
    }

    public void incrementTalentProfileSuccess() {
        increment(talentProfileSuccessCounter);
    }

    public void incrementTalentProfileNotFound() {
        increment(talentProfileNotFoundCounter);
    }

    public void incrementTalentProfileCreated() {
        increment(talentProfileCreatedCounter);
    }

    public void incrementTalentProfileUpdated() {
        increment(talentProfileUpdatedCounter);
    }

    public void incrementTalentProfileDeleted() {
        increment(talentProfileDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
