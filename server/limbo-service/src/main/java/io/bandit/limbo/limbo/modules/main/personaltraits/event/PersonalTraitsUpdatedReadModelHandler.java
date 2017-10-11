package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch.PersonalTraitsQueryModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch.PersonalTraitsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("PersonalTraitsUpdated.ReadModelHandler")
public class PersonalTraitsUpdatedReadModelHandler implements IEventHandler<PersonalTraitsUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PersonalTraitsQueryRepository personalTraitsQueryRepository;

    @Inject
    public PersonalTraitsUpdatedReadModelHandler(
            @Named("PersonalTraitsQueryRepository") final PersonalTraitsQueryRepository personalTraitsQueryRepository) {

        this.personalTraitsQueryRepository = personalTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final PersonalTraitsUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final PersonalTraitsUpdated.Payload payload = event.getPayload();
            final PersonalTraitsUpdated.Attributes attributes = payload.getAttributes();

            final PersonalTraitsQueryModel personalTraits = personalTraitsQueryRepository.findOne(payload.getId());

            personalTraits.setDescription(attributes.getDescription());
            personalTraits.setTalent(attributes.getTalent());

            personalTraitsQueryRepository.save(personalTraits);
        });
    }
}
