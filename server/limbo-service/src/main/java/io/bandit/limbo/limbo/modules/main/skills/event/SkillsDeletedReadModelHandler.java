package io.bandit.limbo.limbo.modules.main.skills.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch.SkillsQueryModel;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch.SkillsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("SkillsDeletedReadModelHandler")
public class SkillsDeletedReadModelHandler implements IEventHandler<SkillsDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SkillsQueryRepository skillsQueryRepository;

    @Inject
    public SkillsDeletedReadModelHandler(
            @Named("SkillsQueryRepository") final SkillsQueryRepository skillsQueryRepository) {

        this.skillsQueryRepository = skillsQueryRepository;
    }

    public CompletableFuture<Void> handle(final SkillsDeleted event){
        return CompletableFuture.runAsync(() -> {
            final SkillsDeleted.Payload payload = event.getPayload();
            final String skillsId = payload.getId();

            skillsQueryRepository.delete(skillsId);
        });
    }
}
