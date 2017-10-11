package io.bandit.limbo.limbo.modules.main.talenttitle.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch.TalentTitleQueryModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch.TalentTitleQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("TalentTitleCreated.ReadModelHandler")
public class TalentTitleCreatedReadModelHandler implements IEventHandler<TalentTitleCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentTitleQueryRepository talentTitleQueryRepository;

    @Inject
    public TalentTitleCreatedReadModelHandler(
            @Named("TalentTitleQueryRepository") final TalentTitleQueryRepository talentTitleQueryRepository) {

        this.talentTitleQueryRepository = talentTitleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentTitleCreated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentTitleCreated.Payload payload = event.getPayload();
            final TalentTitleCreated.Attributes attributes = payload.getAttributes();
            final TalentTitleQueryModel talentTitle = new TalentTitleQueryModel();

            talentTitle.setId(payload.getId());
            talentTitle.setTitle(attributes.getTitle());

            talentTitleQueryRepository.save(talentTitle);
        });
    }
}
