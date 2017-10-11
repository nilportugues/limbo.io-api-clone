package io.bandit.limbo.limbo.application.api.resources.socialnetworks.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class SocialNetworksApiMetrics extends Metrics {

    private static final String SOCIALNETWORKS = ".socialnetworks";
    private static final String SOCIALNETWORKS_TALENT = ".socialnetworks_talent";

    private static Counter socialNetworksErrorCounter;
    private static Counter socialNetworksSuccessCounter;
    private static Counter socialNetworksNotFoundCounter;
    private static Counter socialNetworksCreatedCounter;
    private static Counter socialNetworksUpdatedCounter;
    private static Counter socialNetworksDeletedCounter;

    private static Counter socialNetworksTalentErrorCounter;
    private static Counter socialNetworksTalentSuccessCounter;
    private static Counter socialNetworksTalentNotFoundCounter;
    private static Counter socialNetworksTalentCreatedCounter;
    private static Counter socialNetworksTalentUpdatedCounter;
    private static Counter socialNetworksTalentDeletedCounter;

    public SocialNetworksApiMetrics(final MeterRegistry registry) {
        socialNetworksErrorCounter = registry.counter(API_ERROR + SOCIALNETWORKS);
        socialNetworksSuccessCounter = registry.counter(API_FETCH + SOCIALNETWORKS);
        socialNetworksNotFoundCounter = registry.counter(API_NOTFOUND + SOCIALNETWORKS);
        socialNetworksCreatedCounter = registry.counter(API_CREATED + SOCIALNETWORKS);
        socialNetworksUpdatedCounter = registry.counter(API_UPDATED + SOCIALNETWORKS);
        socialNetworksDeletedCounter = registry.counter(API_DELETED + SOCIALNETWORKS);

        socialNetworksTalentErrorCounter= registry.counter(API_ERROR + SOCIALNETWORKS_TALENT);
        socialNetworksTalentSuccessCounter = registry.counter(API_FETCH + SOCIALNETWORKS_TALENT);
        socialNetworksTalentCreatedCounter = registry.counter(API_CREATED + SOCIALNETWORKS_TALENT);
        socialNetworksTalentUpdatedCounter = registry.counter(API_UPDATED + SOCIALNETWORKS_TALENT);
        socialNetworksTalentDeletedCounter = registry.counter(API_DELETED + SOCIALNETWORKS_TALENT);
        socialNetworksTalentNotFoundCounter = registry.counter(API_NOTFOUND + SOCIALNETWORKS_TALENT);

    }

    public void incrementSocialNetworksError() {
        increment(socialNetworksErrorCounter);
    }

    public void incrementSocialNetworksSuccess() {
        increment(socialNetworksSuccessCounter);
    }

    public void incrementSocialNetworksNotFound() {
        increment(socialNetworksNotFoundCounter);
    }

    public void incrementSocialNetworksCreated() {
        increment(socialNetworksCreatedCounter);
    }

    public void incrementSocialNetworksUpdated() {
        increment(socialNetworksUpdatedCounter);
    }

    public void incrementSocialNetworksDeleted() {
        increment(socialNetworksDeletedCounter);
    }

    public void incrementSocialNetworksTalentError() {
        increment(socialNetworksTalentErrorCounter);
    }

    public void incrementSocialNetworksTalentSuccess() {
        increment(socialNetworksTalentSuccessCounter);
    }

    public void incrementSocialNetworksTalentNotFound() {
        increment(socialNetworksTalentNotFoundCounter);
    }

    public void incrementSocialNetworksTalentCreated() {
        increment(socialNetworksTalentCreatedCounter);
    }

    public void incrementSocialNetworksTalentUpdated() {
        increment(socialNetworksTalentUpdatedCounter);
    }

    public void incrementSocialNetworksTalentDeleted() {
        increment(socialNetworksTalentDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
