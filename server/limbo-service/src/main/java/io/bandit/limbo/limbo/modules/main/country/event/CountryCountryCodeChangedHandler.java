package io.bandit.limbo.limbo.modules.main.country.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch.CountryQueryModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch.CountryQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("CountryCountryCodeChanged.Handler")
public class CountryCountryCodeChangedHandler implements IEventHandler<CountryCountryCodeChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CountryQueryRepository countryQueryRepository;

    @Inject
    public CountryCountryCodeChangedHandler(@Named("CountryQueryRepository") final CountryQueryRepository countryQueryRepository) {

        this.countryQueryRepository = countryQueryRepository;
    }

    public CompletableFuture<Void> handle(final CountryCountryCodeChanged event) {

        return CompletableFuture.runAsync(() -> {
            final CountryCountryCodeChanged.Payload payload = event.getPayload();
            final CountryCountryCodeChanged.Attributes attributes = payload.getAttributes();
            final String countryId = payload.getId();

            //Custom logic here.

        });
    }
}
