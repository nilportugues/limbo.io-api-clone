package io.bandit.limbo.limbo.modules.main.talentexperience.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch.TalentExperienceQueryModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch.TalentExperienceQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("TalentExperienceCreated.ReadModelHandler")
public class TalentExperienceCreatedReadModelHandler implements IEventHandler<TalentExperienceCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentExperienceQueryRepository talentExperienceQueryRepository;

    @Inject
    public TalentExperienceCreatedReadModelHandler(
            @Named("TalentExperienceQueryRepository") final TalentExperienceQueryRepository talentExperienceQueryRepository) {

        this.talentExperienceQueryRepository = talentExperienceQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentExperienceCreated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentExperienceCreated.Payload payload = event.getPayload();
            final TalentExperienceCreated.Attributes attributes = payload.getAttributes();
            final TalentExperienceQueryModel talentExperience = new TalentExperienceQueryModel();

            talentExperience.setId(payload.getId());
            talentExperience.setYears(attributes.getYears());

            talentExperienceQueryRepository.save(talentExperience);
        });
    }
}
