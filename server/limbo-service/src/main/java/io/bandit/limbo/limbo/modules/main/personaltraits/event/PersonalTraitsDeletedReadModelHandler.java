package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch.PersonalTraitsQueryModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch.PersonalTraitsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("PersonalTraitsDeletedReadModelHandler")
public class PersonalTraitsDeletedReadModelHandler implements IEventHandler<PersonalTraitsDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PersonalTraitsQueryRepository personalTraitsQueryRepository;

    @Inject
    public PersonalTraitsDeletedReadModelHandler(
            @Named("PersonalTraitsQueryRepository") final PersonalTraitsQueryRepository personalTraitsQueryRepository) {

        this.personalTraitsQueryRepository = personalTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final PersonalTraitsDeleted event){
        return CompletableFuture.runAsync(() -> {
            final PersonalTraitsDeleted.Payload payload = event.getPayload();
            final String personalTraitsId = payload.getId();

            personalTraitsQueryRepository.delete(personalTraitsId);
        });
    }
}
