package io.bandit.limbo.limbo.modules.main.notableprojects;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.notableprojects.commands.*;
import io.bandit.limbo.limbo.modules.main.notableprojects.queries.*;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotableProjectsModule {

    public NotableProjectsModule(final CommandRegistry commandRegistry, 
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
        registry.register(NotableProjectsPersist.Command.class, "NotableProjectsPersist.CommandHandler");
        registry.register(NotableProjectsDelete.Command.class, "NotableProjectsDelete.CommandHandler");
        registry.register(NotableProjectsTalentPersist.Command.class, "NotableProjectsTalentPersist.CommandHandler");
        registry.register(NotableProjectsTalentDelete.Command.class, "NotableProjectsTalentDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetNotableProjects.Query.class, "GetNotableProjects.QueryHandler");
        registry.register(NotableProjectsList.Query.class, "NotableProjectsList.QueryHandler");
        registry.register(NotableProjectsPaginated.Query.class, "NotableProjectsPaginated.QueryHandler");
        registry.register(NotableProjectsTalent.Query.class, "NotableProjectsTalent.QueryHandler");
        registry.register(NotableProjectsTalentPaginated.Query.class, "NotableProjectsTalentPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(NotableProjectsCreated.class, "NotableProjectsCreated.ReadModelHandler");
        registry.register(NotableProjectsUpdated.class, "NotableProjectsUpdated.ReadModelHandler");
        registry.register(NotableProjectsDeleted.class, "NotableProjectsDeleted.ReadModelHandler");

        //NotableProjects properties
        registry.register(NotableProjectsTitleChanged.class, "NotableProjectsTitleChanged.Handler");
        registry.register(NotableProjectsDescriptionChanged.class, "NotableProjectsDescriptionChanged.Handler");

        //NotableProjects's Talent events
        registry.register(NotableProjectsTalentChanged.class, "NotableProjectsTalentChanged.ReadModelHandler");
        registry.register(NotableProjectsTalentRemoved.class, "NotableProjectsTalentRemoved.ReadModelHandler");
            }

}
