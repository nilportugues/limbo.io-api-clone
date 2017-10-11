package io.bandit.limbo.limbo.modules.main.worktype.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch.WorkTypeQueryModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch.WorkTypeQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("WorkTypeDeletedReadModelHandler")
public class WorkTypeDeletedReadModelHandler implements IEventHandler<WorkTypeDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final WorkTypeQueryRepository workTypeQueryRepository;

    @Inject
    public WorkTypeDeletedReadModelHandler(
            @Named("WorkTypeQueryRepository") final WorkTypeQueryRepository workTypeQueryRepository) {

        this.workTypeQueryRepository = workTypeQueryRepository;
    }

    public CompletableFuture<Void> handle(final WorkTypeDeleted event){
        return CompletableFuture.runAsync(() -> {
            final WorkTypeDeleted.Payload payload = event.getPayload();
            final String workTypeId = payload.getId();

            workTypeQueryRepository.delete(workTypeId);
        });
    }
}
