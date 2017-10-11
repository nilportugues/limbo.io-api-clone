package io.bandit.limbo.limbo.modules.main.talentrole.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch.TalentRoleQueryModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch.TalentRoleQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("TalentRoleDeletedReadModelHandler")
public class TalentRoleDeletedReadModelHandler implements IEventHandler<TalentRoleDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentRoleQueryRepository talentRoleQueryRepository;

    @Inject
    public TalentRoleDeletedReadModelHandler(
            @Named("TalentRoleQueryRepository") final TalentRoleQueryRepository talentRoleQueryRepository) {

        this.talentRoleQueryRepository = talentRoleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentRoleDeleted event){
        return CompletableFuture.runAsync(() -> {
            final TalentRoleDeleted.Payload payload = event.getPayload();
            final String talentRoleId = payload.getId();

            talentRoleQueryRepository.delete(talentRoleId);
        });
    }
}
