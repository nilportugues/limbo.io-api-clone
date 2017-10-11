package io.bandit.limbo.limbo.modules.main.talent.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch.TalentQueryModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch.TalentQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("TalentPasswordChanged.Handler")
public class TalentPasswordChangedHandler implements IEventHandler<TalentPasswordChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentQueryRepository talentQueryRepository;

    @Inject
    public TalentPasswordChangedHandler(@Named("TalentQueryRepository") final TalentQueryRepository talentQueryRepository) {

        this.talentQueryRepository = talentQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentPasswordChanged event) {

        return CompletableFuture.runAsync(() -> {
            final TalentPasswordChanged.Payload payload = event.getPayload();
            final TalentPasswordChanged.Attributes attributes = payload.getAttributes();
            final String talentId = payload.getId();

            //Custom logic here.

        });
    }
}
