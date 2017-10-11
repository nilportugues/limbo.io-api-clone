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


@Named("CityCreated.ReadModelHandler")
public class CityCreatedReadModelHandler implements IEventHandler<CityCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CityQueryRepository cityQueryRepository;

    @Inject
    public CityCreatedReadModelHandler(
            @Named("CityQueryRepository") final CityQueryRepository cityQueryRepository) {

        this.cityQueryRepository = cityQueryRepository;
    }

    public CompletableFuture<Void> handle(final CityCreated event) {
        return CompletableFuture.runAsync(() -> {
            final CityCreated.Payload payload = event.getPayload();
            final CityCreated.Attributes attributes = payload.getAttributes();
            final CityQueryModel city = new CityQueryModel();

            city.setId(payload.getId());
            city.setName(attributes.getName());
            city.setCountry(attributes.getCountry());

            cityQueryRepository.save(city);
        });
    }
}
