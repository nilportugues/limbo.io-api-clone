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

@Named("CountryNameChanged.Handler")
public class CountryNameChangedHandler implements IEventHandler<CountryNameChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CountryQueryRepository countryQueryRepository;

    @Inject
    public CountryNameChangedHandler(@Named("CountryQueryRepository") final CountryQueryRepository countryQueryRepository) {

        this.countryQueryRepository = countryQueryRepository;
    }

    public CompletableFuture<Void> handle(final CountryNameChanged event) {

        return CompletableFuture.runAsync(() -> {
            final CountryNameChanged.Payload payload = event.getPayload();
            final CountryNameChanged.Attributes attributes = payload.getAttributes();
            final String countryId = payload.getId();

            //Custom logic here.

        });
    }
}
