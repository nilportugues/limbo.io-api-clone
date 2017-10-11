package io.bandit.limbo.limbo.modules.main.talenttitle.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch.TalentTitleQueryModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch.TalentTitleQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("TalentTitleDeletedReadModelHandler")
public class TalentTitleDeletedReadModelHandler implements IEventHandler<TalentTitleDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentTitleQueryRepository talentTitleQueryRepository;

    @Inject
    public TalentTitleDeletedReadModelHandler(
            @Named("TalentTitleQueryRepository") final TalentTitleQueryRepository talentTitleQueryRepository) {

        this.talentTitleQueryRepository = talentTitleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentTitleDeleted event){
        return CompletableFuture.runAsync(() -> {
            final TalentTitleDeleted.Payload payload = event.getPayload();
            final String talentTitleId = payload.getId();

            talentTitleQueryRepository.delete(talentTitleId);
        });
    }
}
