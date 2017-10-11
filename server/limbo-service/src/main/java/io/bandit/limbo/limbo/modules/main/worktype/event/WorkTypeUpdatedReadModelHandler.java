package io.bandit.limbo.limbo.modules.main.worktype.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch.WorkTypeQueryModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch.WorkTypeQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("WorkTypeUpdated.ReadModelHandler")
public class WorkTypeUpdatedReadModelHandler implements IEventHandler<WorkTypeUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final WorkTypeQueryRepository workTypeQueryRepository;

    @Inject
    public WorkTypeUpdatedReadModelHandler(
            @Named("WorkTypeQueryRepository") final WorkTypeQueryRepository workTypeQueryRepository) {

        this.workTypeQueryRepository = workTypeQueryRepository;
    }

    public CompletableFuture<Void> handle(final WorkTypeUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final WorkTypeUpdated.Payload payload = event.getPayload();
            final WorkTypeUpdated.Attributes attributes = payload.getAttributes();

            final WorkTypeQueryModel workType = workTypeQueryRepository.findOne(payload.getId());

            workType.setWorkType(attributes.getWorkType());
            workType.setDescription(attributes.getDescription());

            workTypeQueryRepository.save(workType);
        });
    }
}
