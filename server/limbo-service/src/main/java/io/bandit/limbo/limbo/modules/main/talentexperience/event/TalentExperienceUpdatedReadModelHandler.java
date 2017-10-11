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


@Named("TalentExperienceUpdated.ReadModelHandler")
public class TalentExperienceUpdatedReadModelHandler implements IEventHandler<TalentExperienceUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentExperienceQueryRepository talentExperienceQueryRepository;

    @Inject
    public TalentExperienceUpdatedReadModelHandler(
            @Named("TalentExperienceQueryRepository") final TalentExperienceQueryRepository talentExperienceQueryRepository) {

        this.talentExperienceQueryRepository = talentExperienceQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentExperienceUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentExperienceUpdated.Payload payload = event.getPayload();
            final TalentExperienceUpdated.Attributes attributes = payload.getAttributes();

            final TalentExperienceQueryModel talentExperience = talentExperienceQueryRepository.findOne(payload.getId());

            talentExperience.setYears(attributes.getYears());

            talentExperienceQueryRepository.save(talentExperience);
        });
    }
}
