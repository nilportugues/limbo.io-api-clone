package io.bandit.limbo.limbo.modules.main.talentprofile.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch.TalentProfileQueryModel;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch.TalentProfileQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("TalentProfileLastNameChanged.Handler")
public class TalentProfileLastNameChangedHandler implements IEventHandler<TalentProfileLastNameChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentProfileQueryRepository talentProfileQueryRepository;

    @Inject
    public TalentProfileLastNameChangedHandler(@Named("TalentProfileQueryRepository") final TalentProfileQueryRepository talentProfileQueryRepository) {

        this.talentProfileQueryRepository = talentProfileQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentProfileLastNameChanged event) {

        return CompletableFuture.runAsync(() -> {
            final TalentProfileLastNameChanged.Payload payload = event.getPayload();
            final TalentProfileLastNameChanged.Attributes attributes = payload.getAttributes();
            final String talentProfileId = payload.getId();

            //Custom logic here.

        });
    }
}
