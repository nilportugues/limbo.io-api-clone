package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch.SocialNetworksQueryModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch.SocialNetworksQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("SocialNetworksUpdated.ReadModelHandler")
public class SocialNetworksUpdatedReadModelHandler implements IEventHandler<SocialNetworksUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SocialNetworksQueryRepository socialNetworksQueryRepository;

    @Inject
    public SocialNetworksUpdatedReadModelHandler(
            @Named("SocialNetworksQueryRepository") final SocialNetworksQueryRepository socialNetworksQueryRepository) {

        this.socialNetworksQueryRepository = socialNetworksQueryRepository;
    }

    public CompletableFuture<Void> handle(final SocialNetworksUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final SocialNetworksUpdated.Payload payload = event.getPayload();
            final SocialNetworksUpdated.Attributes attributes = payload.getAttributes();

            final SocialNetworksQueryModel socialNetworks = socialNetworksQueryRepository.findOne(payload.getId());

            socialNetworks.setName(attributes.getName());
            socialNetworks.setUrl(attributes.getUrl());
            socialNetworks.setTalent(attributes.getTalent());

            socialNetworksQueryRepository.save(socialNetworks);
        });
    }
}
