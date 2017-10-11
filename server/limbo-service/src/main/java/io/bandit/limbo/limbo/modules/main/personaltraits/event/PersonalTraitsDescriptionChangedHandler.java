package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch.PersonalTraitsQueryModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch.PersonalTraitsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("PersonalTraitsDescriptionChanged.Handler")
public class PersonalTraitsDescriptionChangedHandler implements IEventHandler<PersonalTraitsDescriptionChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PersonalTraitsQueryRepository personalTraitsQueryRepository;

    @Inject
    public PersonalTraitsDescriptionChangedHandler(@Named("PersonalTraitsQueryRepository") final PersonalTraitsQueryRepository personalTraitsQueryRepository) {

        this.personalTraitsQueryRepository = personalTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final PersonalTraitsDescriptionChanged event) {

        return CompletableFuture.runAsync(() -> {
            final PersonalTraitsDescriptionChanged.Payload payload = event.getPayload();
            final PersonalTraitsDescriptionChanged.Attributes attributes = payload.getAttributes();
            final String personalTraitsId = payload.getId();

            //Custom logic here.

        });
    }
}
