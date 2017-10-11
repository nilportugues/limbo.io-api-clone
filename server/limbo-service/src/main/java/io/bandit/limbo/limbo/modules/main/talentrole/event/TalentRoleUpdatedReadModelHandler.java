package io.bandit.limbo.limbo.modules.main.talentrole.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch.TalentRoleQueryModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch.TalentRoleQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("TalentRoleUpdated.ReadModelHandler")
public class TalentRoleUpdatedReadModelHandler implements IEventHandler<TalentRoleUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentRoleQueryRepository talentRoleQueryRepository;

    @Inject
    public TalentRoleUpdatedReadModelHandler(
            @Named("TalentRoleQueryRepository") final TalentRoleQueryRepository talentRoleQueryRepository) {

        this.talentRoleQueryRepository = talentRoleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentRoleUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentRoleUpdated.Payload payload = event.getPayload();
            final TalentRoleUpdated.Attributes attributes = payload.getAttributes();

            final TalentRoleQueryModel talentRole = talentRoleQueryRepository.findOne(payload.getId());

            talentRole.setTitle(attributes.getTitle());
            talentRole.setDescription(attributes.getDescription());

            talentRoleQueryRepository.save(talentRole);
        });
    }
}
