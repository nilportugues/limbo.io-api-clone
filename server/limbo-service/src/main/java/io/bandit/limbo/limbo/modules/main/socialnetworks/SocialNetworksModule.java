package io.bandit.limbo.limbo.modules.main.socialnetworks;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.socialnetworks.commands.*;
import io.bandit.limbo.limbo.modules.main.socialnetworks.queries.*;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialNetworksModule {

    public SocialNetworksModule(final CommandRegistry commandRegistry, 
                                    final QueryRegistry queryRegistry, 
                                    final EventRegistry eventRegistry) {
        
        registerCommandHandlers(commandRegistry);
        registerQueryHandlers(queryRegistry);
        registerEventHandlers(eventRegistry);
    }

    /**
     * A Command must be mapped to a CommandHandler.
     */
    private void registerCommandHandlers(final CommandRegistry registry) {
        registry.register(SocialNetworksPersist.Command.class, "SocialNetworksPersist.CommandHandler");
        registry.register(SocialNetworksDelete.Command.class, "SocialNetworksDelete.CommandHandler");
        registry.register(SocialNetworksTalentPersist.Command.class, "SocialNetworksTalentPersist.CommandHandler");
        registry.register(SocialNetworksTalentDelete.Command.class, "SocialNetworksTalentDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetSocialNetworks.Query.class, "GetSocialNetworks.QueryHandler");
        registry.register(SocialNetworksList.Query.class, "SocialNetworksList.QueryHandler");
        registry.register(SocialNetworksPaginated.Query.class, "SocialNetworksPaginated.QueryHandler");
        registry.register(SocialNetworksTalent.Query.class, "SocialNetworksTalent.QueryHandler");
        registry.register(SocialNetworksTalentPaginated.Query.class, "SocialNetworksTalentPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(SocialNetworksCreated.class, "SocialNetworksCreated.ReadModelHandler");
        registry.register(SocialNetworksUpdated.class, "SocialNetworksUpdated.ReadModelHandler");
        registry.register(SocialNetworksDeleted.class, "SocialNetworksDeleted.ReadModelHandler");

        //SocialNetworks properties
        registry.register(SocialNetworksNameChanged.class, "SocialNetworksNameChanged.Handler");
        registry.register(SocialNetworksUrlChanged.class, "SocialNetworksUrlChanged.Handler");

        //SocialNetworks's Talent events
        registry.register(SocialNetworksTalentChanged.class, "SocialNetworksTalentChanged.ReadModelHandler");
        registry.register(SocialNetworksTalentRemoved.class, "SocialNetworksTalentRemoved.ReadModelHandler");
            }

}
