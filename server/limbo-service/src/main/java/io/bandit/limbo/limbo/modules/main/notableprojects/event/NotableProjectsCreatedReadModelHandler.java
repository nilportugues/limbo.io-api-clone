package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch.NotableProjectsQueryModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch.NotableProjectsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("NotableProjectsCreated.ReadModelHandler")
public class NotableProjectsCreatedReadModelHandler implements IEventHandler<NotableProjectsCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final NotableProjectsQueryRepository notableProjectsQueryRepository;

    @Inject
    public NotableProjectsCreatedReadModelHandler(
            @Named("NotableProjectsQueryRepository") final NotableProjectsQueryRepository notableProjectsQueryRepository) {

        this.notableProjectsQueryRepository = notableProjectsQueryRepository;
    }

    public CompletableFuture<Void> handle(final NotableProjectsCreated event) {
        return CompletableFuture.runAsync(() -> {
            final NotableProjectsCreated.Payload payload = event.getPayload();
            final NotableProjectsCreated.Attributes attributes = payload.getAttributes();
            final NotableProjectsQueryModel notableProjects = new NotableProjectsQueryModel();

            notableProjects.setId(payload.getId());
            notableProjects.setTitle(attributes.getTitle());
            notableProjects.setDescription(attributes.getDescription());
            notableProjects.setTalent(attributes.getTalent());

            notableProjectsQueryRepository.save(notableProjects);
        });
    }
}
