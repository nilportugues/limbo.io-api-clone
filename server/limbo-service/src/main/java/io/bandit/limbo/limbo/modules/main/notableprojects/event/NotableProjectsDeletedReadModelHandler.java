package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch.NotableProjectsQueryModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch.NotableProjectsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("NotableProjectsDeletedReadModelHandler")
public class NotableProjectsDeletedReadModelHandler implements IEventHandler<NotableProjectsDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final NotableProjectsQueryRepository notableProjectsQueryRepository;

    @Inject
    public NotableProjectsDeletedReadModelHandler(
            @Named("NotableProjectsQueryRepository") final NotableProjectsQueryRepository notableProjectsQueryRepository) {

        this.notableProjectsQueryRepository = notableProjectsQueryRepository;
    }

    public CompletableFuture<Void> handle(final NotableProjectsDeleted event){
        return CompletableFuture.runAsync(() -> {
            final NotableProjectsDeleted.Payload payload = event.getPayload();
            final String notableProjectsId = payload.getId();

            notableProjectsQueryRepository.delete(notableProjectsId);
        });
    }
}
