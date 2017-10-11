package io.bandit.limbo.limbo.modules.main.country.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch.CountryQueryModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch.CountryQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("CountryCreated.ReadModelHandler")
public class CountryCreatedReadModelHandler implements IEventHandler<CountryCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CountryQueryRepository countryQueryRepository;

    @Inject
    public CountryCreatedReadModelHandler(
            @Named("CountryQueryRepository") final CountryQueryRepository countryQueryRepository) {

        this.countryQueryRepository = countryQueryRepository;
    }

    public CompletableFuture<Void> handle(final CountryCreated event) {
        return CompletableFuture.runAsync(() -> {
            final CountryCreated.Payload payload = event.getPayload();
            final CountryCreated.Attributes attributes = payload.getAttributes();
            final CountryQueryModel country = new CountryQueryModel();

            country.setId(payload.getId());
            country.setName(attributes.getName());
            country.setCountryCode(attributes.getCountryCode());
            country.setCities(attributes.getCities());

            countryQueryRepository.save(country);
        });
    }
}
