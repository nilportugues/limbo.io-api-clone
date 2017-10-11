package io.bandit.limbo.limbo.application.api.resources.skills.metrics;

import io.bandit.limbo.limbo.modules.shared.metrics.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class SkillsApiMetrics extends Metrics {

    private static final String SKILLS = ".skills";
    private static final String SKILLS_TALENT = ".skills_talent";

    private static Counter skillsErrorCounter;
    private static Counter skillsSuccessCounter;
    private static Counter skillsNotFoundCounter;
    private static Counter skillsCreatedCounter;
    private static Counter skillsUpdatedCounter;
    private static Counter skillsDeletedCounter;

    private static Counter skillsTalentErrorCounter;
    private static Counter skillsTalentSuccessCounter;
    private static Counter skillsTalentNotFoundCounter;
    private static Counter skillsTalentCreatedCounter;
    private static Counter skillsTalentUpdatedCounter;
    private static Counter skillsTalentDeletedCounter;

    public SkillsApiMetrics(final MeterRegistry registry) {
        skillsErrorCounter = registry.counter(API_ERROR + SKILLS);
        skillsSuccessCounter = registry.counter(API_FETCH + SKILLS);
        skillsNotFoundCounter = registry.counter(API_NOTFOUND + SKILLS);
        skillsCreatedCounter = registry.counter(API_CREATED + SKILLS);
        skillsUpdatedCounter = registry.counter(API_UPDATED + SKILLS);
        skillsDeletedCounter = registry.counter(API_DELETED + SKILLS);

        skillsTalentErrorCounter= registry.counter(API_ERROR + SKILLS_TALENT);
        skillsTalentSuccessCounter = registry.counter(API_FETCH + SKILLS_TALENT);
        skillsTalentCreatedCounter = registry.counter(API_CREATED + SKILLS_TALENT);
        skillsTalentUpdatedCounter = registry.counter(API_UPDATED + SKILLS_TALENT);
        skillsTalentDeletedCounter = registry.counter(API_DELETED + SKILLS_TALENT);
        skillsTalentNotFoundCounter = registry.counter(API_NOTFOUND + SKILLS_TALENT);

    }

    public void incrementSkillsError() {
        increment(skillsErrorCounter);
    }

    public void incrementSkillsSuccess() {
        increment(skillsSuccessCounter);
    }

    public void incrementSkillsNotFound() {
        increment(skillsNotFoundCounter);
    }

    public void incrementSkillsCreated() {
        increment(skillsCreatedCounter);
    }

    public void incrementSkillsUpdated() {
        increment(skillsUpdatedCounter);
    }

    public void incrementSkillsDeleted() {
        increment(skillsDeletedCounter);
    }

    public void incrementSkillsTalentError() {
        increment(skillsTalentErrorCounter);
    }

    public void incrementSkillsTalentSuccess() {
        increment(skillsTalentSuccessCounter);
    }

    public void incrementSkillsTalentNotFound() {
        increment(skillsTalentNotFoundCounter);
    }

    public void incrementSkillsTalentCreated() {
        increment(skillsTalentCreatedCounter);
    }

    public void incrementSkillsTalentUpdated() {
        increment(skillsTalentUpdatedCounter);
    }

    public void incrementSkillsTalentDeleted() {
        increment(skillsTalentDeletedCounter);
    }


    private void increment(final Counter counter) {
        CompletableFuture.runAsync(counter::increment);
    }
}
