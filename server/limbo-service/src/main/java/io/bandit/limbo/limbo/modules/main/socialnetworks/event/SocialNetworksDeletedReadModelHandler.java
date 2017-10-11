package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch.SocialNetworksQueryModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch.SocialNetworksQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("SocialNetworksDeletedReadModelHandler")
public class SocialNetworksDeletedReadModelHandler implements IEventHandler<SocialNetworksDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SocialNetworksQueryRepository socialNetworksQueryRepository;

    @Inject
    public SocialNetworksDeletedReadModelHandler(
            @Named("SocialNetworksQueryRepository") final SocialNetworksQueryRepository socialNetworksQueryRepository) {

        this.socialNetworksQueryRepository = socialNetworksQueryRepository;
    }

    public CompletableFuture<Void> handle(final SocialNetworksDeleted event){
        return CompletableFuture.runAsync(() -> {
            final SocialNetworksDeleted.Payload payload = event.getPayload();
            final String socialNetworksId = payload.getId();

            socialNetworksQueryRepository.delete(socialNetworksId);
        });
    }
}
