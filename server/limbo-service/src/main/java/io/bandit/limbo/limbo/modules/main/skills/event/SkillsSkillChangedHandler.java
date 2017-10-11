package io.bandit.limbo.limbo.modules.main.skills.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch.SkillsQueryModel;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch.SkillsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("SkillsSkillChanged.Handler")
public class SkillsSkillChangedHandler implements IEventHandler<SkillsSkillChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SkillsQueryRepository skillsQueryRepository;

    @Inject
    public SkillsSkillChangedHandler(@Named("SkillsQueryRepository") final SkillsQueryRepository skillsQueryRepository) {

        this.skillsQueryRepository = skillsQueryRepository;
    }

    public CompletableFuture<Void> handle(final SkillsSkillChanged event) {

        return CompletableFuture.runAsync(() -> {
            final SkillsSkillChanged.Payload payload = event.getPayload();
            final SkillsSkillChanged.Attributes attributes = payload.getAttributes();
            final String skillsId = payload.getId();

            //Custom logic here.

        });
    }
}
