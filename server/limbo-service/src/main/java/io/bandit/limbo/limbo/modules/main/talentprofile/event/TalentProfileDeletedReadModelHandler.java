package io.bandit.limbo.limbo.modules.main.talentprofile.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch.TalentProfileQueryModel;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch.TalentProfileQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("TalentProfileDeletedReadModelHandler")
public class TalentProfileDeletedReadModelHandler implements IEventHandler<TalentProfileDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentProfileQueryRepository talentProfileQueryRepository;

    @Inject
    public TalentProfileDeletedReadModelHandler(
            @Named("TalentProfileQueryRepository") final TalentProfileQueryRepository talentProfileQueryRepository) {

        this.talentProfileQueryRepository = talentProfileQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentProfileDeleted event){
        return CompletableFuture.runAsync(() -> {
            final TalentProfileDeleted.Payload payload = event.getPayload();
            final String talentProfileId = payload.getId();

            talentProfileQueryRepository.delete(talentProfileId);
        });
    }
}
