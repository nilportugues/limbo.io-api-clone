package io.bandit.limbo.limbo.application.api.resources.talenttitle.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class TalentTitleApiMetrics extends Metrics {

    private static final String TALENTTITLE = ".talenttitle";

    private static Counter talentTitleErrorCounter;
    private static Counter talentTitleSuccessCounter;
    private static Counter talentTitleNotFoundCounter;
    private static Counter talentTitleCreatedCounter;
    private static Counter talentTitleUpdatedCounter;
    private static Counter talentTitleDeletedCounter;

    public TalentTitleApiMetrics(final MeterRegistry registry) {
        talentTitleErrorCounter = registry.counter(API_ERROR + TALENTTITLE);
        talentTitleSuccessCounter = registry.counter(API_FETCH + TALENTTITLE);
        talentTitleNotFoundCounter = registry.counter(API_NOTFOUND + TALENTTITLE);
        talentTitleCreatedCounter = registry.counter(API_CREATED + TALENTTITLE);
        talentTitleUpdatedCounter = registry.counter(API_UPDATED + TALENTTITLE);
        talentTitleDeletedCounter = registry.counter(API_DELETED + TALENTTITLE);

    }

    public void incrementTalentTitleError() {
        increment(talentTitleErrorCounter);
    }

    public void incrementTalentTitleSuccess() {
        increment(talentTitleSuccessCounter);
    }

    public void incrementTalentTitleNotFound() {
        increment(talentTitleNotFoundCounter);
    }

    public void incrementTalentTitleCreated() {
        increment(talentTitleCreatedCounter);
    }

    public void incrementTalentTitleUpdated() {
        increment(talentTitleUpdatedCounter);
    }

    public void incrementTalentTitleDeleted() {
        increment(talentTitleDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
