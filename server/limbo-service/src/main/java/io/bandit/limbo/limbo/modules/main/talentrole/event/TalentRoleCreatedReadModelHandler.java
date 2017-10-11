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


@Named("TalentRoleCreated.ReadModelHandler")
public class TalentRoleCreatedReadModelHandler implements IEventHandler<TalentRoleCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentRoleQueryRepository talentRoleQueryRepository;

    @Inject
    public TalentRoleCreatedReadModelHandler(
            @Named("TalentRoleQueryRepository") final TalentRoleQueryRepository talentRoleQueryRepository) {

        this.talentRoleQueryRepository = talentRoleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentRoleCreated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentRoleCreated.Payload payload = event.getPayload();
            final TalentRoleCreated.Attributes attributes = payload.getAttributes();
            final TalentRoleQueryModel talentRole = new TalentRoleQueryModel();

            talentRole.setId(payload.getId());
            talentRole.setTitle(attributes.getTitle());
            talentRole.setDescription(attributes.getDescription());

            talentRoleQueryRepository.save(talentRole);
        });
    }
}
