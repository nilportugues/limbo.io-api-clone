package io.bandit.limbo.limbo.modules.main.talent.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch.TalentQueryModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch.TalentQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("TalentDeletedReadModelHandler")
public class TalentDeletedReadModelHandler implements IEventHandler<TalentDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentQueryRepository talentQueryRepository;

    @Inject
    public TalentDeletedReadModelHandler(
            @Named("TalentQueryRepository") final TalentQueryRepository talentQueryRepository) {

        this.talentQueryRepository = talentQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentDeleted event){
        return CompletableFuture.runAsync(() -> {
            final TalentDeleted.Payload payload = event.getPayload();
            final String talentId = payload.getId();

            talentQueryRepository.delete(talentId);
        });
    }
}
