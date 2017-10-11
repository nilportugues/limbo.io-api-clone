package io.bandit.limbo.limbo.modules.main.talentprofile.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch.TalentProfileQueryModel;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch.TalentProfileQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("TalentProfileUpdated.ReadModelHandler")
public class TalentProfileUpdatedReadModelHandler implements IEventHandler<TalentProfileUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentProfileQueryRepository talentProfileQueryRepository;

    @Inject
    public TalentProfileUpdatedReadModelHandler(
            @Named("TalentProfileQueryRepository") final TalentProfileQueryRepository talentProfileQueryRepository) {

        this.talentProfileQueryRepository = talentProfileQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentProfileUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentProfileUpdated.Payload payload = event.getPayload();
            final TalentProfileUpdated.Attributes attributes = payload.getAttributes();

            final TalentProfileQueryModel talentProfile = talentProfileQueryRepository.findOne(payload.getId());

            talentProfile.setFirstName(attributes.getFirstName());
            talentProfile.setLastName(attributes.getLastName());

            talentProfileQueryRepository.save(talentProfile);
        });
    }
}
