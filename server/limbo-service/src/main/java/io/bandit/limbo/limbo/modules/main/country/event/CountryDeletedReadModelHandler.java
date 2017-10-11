package io.bandit.limbo.limbo.modules.main.country.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch.CountryQueryModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch.CountryQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("CountryDeletedReadModelHandler")
public class CountryDeletedReadModelHandler implements IEventHandler<CountryDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CountryQueryRepository countryQueryRepository;

    @Inject
    public CountryDeletedReadModelHandler(
            @Named("CountryQueryRepository") final CountryQueryRepository countryQueryRepository) {

        this.countryQueryRepository = countryQueryRepository;
    }

    public CompletableFuture<Void> handle(final CountryDeleted event){
        return CompletableFuture.runAsync(() -> {
            final CountryDeleted.Payload payload = event.getPayload();
            final String countryId = payload.getId();

            countryQueryRepository.delete(countryId);
        });
    }
}
