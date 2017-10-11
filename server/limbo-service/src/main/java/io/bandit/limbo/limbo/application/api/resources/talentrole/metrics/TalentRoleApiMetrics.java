package io.bandit.limbo.limbo.application.api.resources.talentrole.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class TalentRoleApiMetrics extends Metrics {

    private static final String TALENTROLE = ".talentrole";

    private static Counter talentRoleErrorCounter;
    private static Counter talentRoleSuccessCounter;
    private static Counter talentRoleNotFoundCounter;
    private static Counter talentRoleCreatedCounter;
    private static Counter talentRoleUpdatedCounter;
    private static Counter talentRoleDeletedCounter;

    public TalentRoleApiMetrics(final MeterRegistry registry) {
        talentRoleErrorCounter = registry.counter(API_ERROR + TALENTROLE);
        talentRoleSuccessCounter = registry.counter(API_FETCH + TALENTROLE);
        talentRoleNotFoundCounter = registry.counter(API_NOTFOUND + TALENTROLE);
        talentRoleCreatedCounter = registry.counter(API_CREATED + TALENTROLE);
        talentRoleUpdatedCounter = registry.counter(API_UPDATED + TALENTROLE);
        talentRoleDeletedCounter = registry.counter(API_DELETED + TALENTROLE);

    }

    public void incrementTalentRoleError() {
        increment(talentRoleErrorCounter);
    }

    public void incrementTalentRoleSuccess() {
        increment(talentRoleSuccessCounter);
    }

    public void incrementTalentRoleNotFound() {
        increment(talentRoleNotFoundCounter);
    }

    public void incrementTalentRoleCreated() {
        increment(talentRoleCreatedCounter);
    }

    public void incrementTalentRoleUpdated() {
        increment(talentRoleUpdatedCounter);
    }

    public void incrementTalentRoleDeleted() {
        increment(talentRoleDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
