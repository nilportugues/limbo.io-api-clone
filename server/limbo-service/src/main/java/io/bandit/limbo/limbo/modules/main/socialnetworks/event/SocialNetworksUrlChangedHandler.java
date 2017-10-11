package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch.SocialNetworksQueryModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch.SocialNetworksQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("SocialNetworksUrlChanged.Handler")
public class SocialNetworksUrlChangedHandler implements IEventHandler<SocialNetworksUrlChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SocialNetworksQueryRepository socialNetworksQueryRepository;

    @Inject
    public SocialNetworksUrlChangedHandler(@Named("SocialNetworksQueryRepository") final SocialNetworksQueryRepository socialNetworksQueryRepository) {

        this.socialNetworksQueryRepository = socialNetworksQueryRepository;
    }

    public CompletableFuture<Void> handle(final SocialNetworksUrlChanged event) {

        return CompletableFuture.runAsync(() -> {
            final SocialNetworksUrlChanged.Payload payload = event.getPayload();
            final SocialNetworksUrlChanged.Attributes attributes = payload.getAttributes();
            final String socialNetworksId = payload.getId();

            //Custom logic here.

        });
    }
}
