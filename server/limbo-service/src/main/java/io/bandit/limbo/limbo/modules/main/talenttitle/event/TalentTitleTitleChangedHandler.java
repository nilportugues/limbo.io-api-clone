package io.bandit.limbo.limbo.modules.main.talenttitle.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch.TalentTitleQueryModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch.TalentTitleQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("TalentTitleTitleChanged.Handler")
public class TalentTitleTitleChangedHandler implements IEventHandler<TalentTitleTitleChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentTitleQueryRepository talentTitleQueryRepository;

    @Inject
    public TalentTitleTitleChangedHandler(@Named("TalentTitleQueryRepository") final TalentTitleQueryRepository talentTitleQueryRepository) {

        this.talentTitleQueryRepository = talentTitleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentTitleTitleChanged event) {

        return CompletableFuture.runAsync(() -> {
            final TalentTitleTitleChanged.Payload payload = event.getPayload();
            final TalentTitleTitleChanged.Attributes attributes = payload.getAttributes();
            final String talentTitleId = payload.getId();

            //Custom logic here.

        });
    }
}
