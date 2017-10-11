package io.bandit.limbo.limbo.modules.main.talentrole;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.talentrole.commands.*;
import io.bandit.limbo.limbo.modules.main.talentrole.queries.*;
import io.bandit.limbo.limbo.modules.main.talentrole.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TalentRoleModule {

    public TalentRoleModule(final CommandRegistry commandRegistry, 
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
        registry.register(TalentRolePersist.Command.class, "TalentRolePersist.CommandHandler");
        registry.register(TalentRoleDelete.Command.class, "TalentRoleDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetTalentRole.Query.class, "GetTalentRole.QueryHandler");
        registry.register(TalentRoleList.Query.class, "TalentRoleList.QueryHandler");
        registry.register(TalentRolePaginated.Query.class, "TalentRolePaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(TalentRoleCreated.class, "TalentRoleCreated.ReadModelHandler");
        registry.register(TalentRoleUpdated.class, "TalentRoleUpdated.ReadModelHandler");
        registry.register(TalentRoleDeleted.class, "TalentRoleDeleted.ReadModelHandler");

        //TalentRole properties
        registry.register(TalentRoleTitleChanged.class, "TalentRoleTitleChanged.Handler");
        registry.register(TalentRoleDescriptionChanged.class, "TalentRoleDescriptionChanged.Handler");
    }

}
