package io.bandit.limbo.limbo.modules.main.skills.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch.SkillsQueryModel;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch.SkillsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("SkillsUpdated.ReadModelHandler")
public class SkillsUpdatedReadModelHandler implements IEventHandler<SkillsUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SkillsQueryRepository skillsQueryRepository;

    @Inject
    public SkillsUpdatedReadModelHandler(
            @Named("SkillsQueryRepository") final SkillsQueryRepository skillsQueryRepository) {

        this.skillsQueryRepository = skillsQueryRepository;
    }

    public CompletableFuture<Void> handle(final SkillsUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final SkillsUpdated.Payload payload = event.getPayload();
            final SkillsUpdated.Attributes attributes = payload.getAttributes();

            final SkillsQueryModel skills = skillsQueryRepository.findOne(payload.getId());

            skills.setSkill(attributes.getSkill());
            skills.setTalent(attributes.getTalent());

            skillsQueryRepository.save(skills);
        });
    }
}
