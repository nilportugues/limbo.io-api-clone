package io.bandit.limbo.limbo.modules.main.worktype.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch.WorkTypeQueryModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch.WorkTypeQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("WorkTypeWorkTypeChanged.Handler")
public class WorkTypeWorkTypeChangedHandler implements IEventHandler<WorkTypeWorkTypeChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final WorkTypeQueryRepository workTypeQueryRepository;

    @Inject
    public WorkTypeWorkTypeChangedHandler(@Named("WorkTypeQueryRepository") final WorkTypeQueryRepository workTypeQueryRepository) {

        this.workTypeQueryRepository = workTypeQueryRepository;
    }

    public CompletableFuture<Void> handle(final WorkTypeWorkTypeChanged event) {

        return CompletableFuture.runAsync(() -> {
            final WorkTypeWorkTypeChanged.Payload payload = event.getPayload();
            final WorkTypeWorkTypeChanged.Attributes attributes = payload.getAttributes();
            final String workTypeId = payload.getId();

            //Custom logic here.

        });
    }
}
