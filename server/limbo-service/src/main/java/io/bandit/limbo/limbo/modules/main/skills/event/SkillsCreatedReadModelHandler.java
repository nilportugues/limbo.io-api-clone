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


@Named("SkillsCreated.ReadModelHandler")
public class SkillsCreatedReadModelHandler implements IEventHandler<SkillsCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SkillsQueryRepository skillsQueryRepository;

    @Inject
    public SkillsCreatedReadModelHandler(
            @Named("SkillsQueryRepository") final SkillsQueryRepository skillsQueryRepository) {

        this.skillsQueryRepository = skillsQueryRepository;
    }

    public CompletableFuture<Void> handle(final SkillsCreated event) {
        return CompletableFuture.runAsync(() -> {
            final SkillsCreated.Payload payload = event.getPayload();
            final SkillsCreated.Attributes attributes = payload.getAttributes();
            final SkillsQueryModel skills = new SkillsQueryModel();

            skills.setId(payload.getId());
            skills.setSkill(attributes.getSkill());
            skills.setTalent(attributes.getTalent());

            skillsQueryRepository.save(skills);
        });
    }
}
