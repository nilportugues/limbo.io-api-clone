package io.bandit.limbo.limbo.modules.main.talentexperience.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch.TalentExperienceQueryModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch.TalentExperienceQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("TalentExperienceYearsChanged.Handler")
public class TalentExperienceYearsChangedHandler implements IEventHandler<TalentExperienceYearsChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentExperienceQueryRepository talentExperienceQueryRepository;

    @Inject
    public TalentExperienceYearsChangedHandler(@Named("TalentExperienceQueryRepository") final TalentExperienceQueryRepository talentExperienceQueryRepository) {

        this.talentExperienceQueryRepository = talentExperienceQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentExperienceYearsChanged event) {

        return CompletableFuture.runAsync(() -> {
            final TalentExperienceYearsChanged.Payload payload = event.getPayload();
            final TalentExperienceYearsChanged.Attributes attributes = payload.getAttributes();
            final String talentExperienceId = payload.getId();

            //Custom logic here.

        });
    }
}
