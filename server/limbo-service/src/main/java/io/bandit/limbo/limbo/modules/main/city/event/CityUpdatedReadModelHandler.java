package io.bandit.limbo.limbo.modules.main.city.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch.CityQueryModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch.CityQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("CityUpdated.ReadModelHandler")
public class CityUpdatedReadModelHandler implements IEventHandler<CityUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CityQueryRepository cityQueryRepository;

    @Inject
    public CityUpdatedReadModelHandler(
            @Named("CityQueryRepository") final CityQueryRepository cityQueryRepository) {

        this.cityQueryRepository = cityQueryRepository;
    }

    public CompletableFuture<Void> handle(final CityUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final CityUpdated.Payload payload = event.getPayload();
            final CityUpdated.Attributes attributes = payload.getAttributes();

            final CityQueryModel city = cityQueryRepository.findOne(payload.getId());

            city.setName(attributes.getName());
            city.setCountry(attributes.getCountry());

            cityQueryRepository.save(city);
        });
    }
}
