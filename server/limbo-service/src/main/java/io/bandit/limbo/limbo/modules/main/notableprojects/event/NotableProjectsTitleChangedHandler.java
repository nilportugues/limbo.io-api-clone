package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch.NotableProjectsQueryModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch.NotableProjectsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("NotableProjectsTitleChanged.Handler")
public class NotableProjectsTitleChangedHandler implements IEventHandler<NotableProjectsTitleChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final NotableProjectsQueryRepository notableProjectsQueryRepository;

    @Inject
    public NotableProjectsTitleChangedHandler(@Named("NotableProjectsQueryRepository") final NotableProjectsQueryRepository notableProjectsQueryRepository) {

        this.notableProjectsQueryRepository = notableProjectsQueryRepository;
    }

    public CompletableFuture<Void> handle(final NotableProjectsTitleChanged event) {

        return CompletableFuture.runAsync(() -> {
            final NotableProjectsTitleChanged.Payload payload = event.getPayload();
            final NotableProjectsTitleChanged.Attributes attributes = payload.getAttributes();
            final String notableProjectsId = payload.getId();

            //Custom logic here.

        });
    }
}
