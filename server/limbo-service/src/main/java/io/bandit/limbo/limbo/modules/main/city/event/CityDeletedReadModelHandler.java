package io.bandit.limbo.limbo.modules.main.city.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch.CityQueryModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch.CityQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("CityDeletedReadModelHandler")
public class CityDeletedReadModelHandler implements IEventHandler<CityDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CityQueryRepository cityQueryRepository;

    @Inject
    public CityDeletedReadModelHandler(
            @Named("CityQueryRepository") final CityQueryRepository cityQueryRepository) {

        this.cityQueryRepository = cityQueryRepository;
    }

    public CompletableFuture<Void> handle(final CityDeleted event){
        return CompletableFuture.runAsync(() -> {
            final CityDeleted.Payload payload = event.getPayload();
            final String cityId = payload.getId();

            cityQueryRepository.delete(cityId);
        });
    }
}
