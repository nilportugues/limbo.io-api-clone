package io.bandit.limbo.limbo.modules.main.talentexperience.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch.TalentExperienceQueryModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch.TalentExperienceQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("TalentExperienceDeletedReadModelHandler")
public class TalentExperienceDeletedReadModelHandler implements IEventHandler<TalentExperienceDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentExperienceQueryRepository talentExperienceQueryRepository;

    @Inject
    public TalentExperienceDeletedReadModelHandler(
            @Named("TalentExperienceQueryRepository") final TalentExperienceQueryRepository talentExperienceQueryRepository) {

        this.talentExperienceQueryRepository = talentExperienceQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentExperienceDeleted event){
        return CompletableFuture.runAsync(() -> {
            final TalentExperienceDeleted.Payload payload = event.getPayload();
            final String talentExperienceId = payload.getId();

            talentExperienceQueryRepository.delete(talentExperienceId);
        });
    }
}
