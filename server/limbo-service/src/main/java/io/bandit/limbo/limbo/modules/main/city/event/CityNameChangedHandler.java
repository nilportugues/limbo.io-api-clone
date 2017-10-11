package io.bandit.limbo.limbo.modules.main.city.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch.CityQueryModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch.CityQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("CityNameChanged.Handler")
public class CityNameChangedHandler implements IEventHandler<CityNameChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CityQueryRepository cityQueryRepository;

    @Inject
    public CityNameChangedHandler(@Named("CityQueryRepository") final CityQueryRepository cityQueryRepository) {

        this.cityQueryRepository = cityQueryRepository;
    }

    public CompletableFuture<Void> handle(final CityNameChanged event) {

        return CompletableFuture.runAsync(() -> {
            final CityNameChanged.Payload payload = event.getPayload();
            final CityNameChanged.Attributes attributes = payload.getAttributes();
            final String cityId = payload.getId();

            //Custom logic here.

        });
    }
}
