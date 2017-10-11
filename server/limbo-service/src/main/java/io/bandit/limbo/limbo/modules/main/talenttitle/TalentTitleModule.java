package io.bandit.limbo.limbo.modules.main.talenttitle;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.talenttitle.commands.*;
import io.bandit.limbo.limbo.modules.main.talenttitle.queries.*;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TalentTitleModule {

    public TalentTitleModule(final CommandRegistry commandRegistry, 
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
        registry.register(TalentTitlePersist.Command.class, "TalentTitlePersist.CommandHandler");
        registry.register(TalentTitleDelete.Command.class, "TalentTitleDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetTalentTitle.Query.class, "GetTalentTitle.QueryHandler");
        registry.register(TalentTitleList.Query.class, "TalentTitleList.QueryHandler");
        registry.register(TalentTitlePaginated.Query.class, "TalentTitlePaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(TalentTitleCreated.class, "TalentTitleCreated.ReadModelHandler");
        registry.register(TalentTitleUpdated.class, "TalentTitleUpdated.ReadModelHandler");
        registry.register(TalentTitleDeleted.class, "TalentTitleDeleted.ReadModelHandler");

        //TalentTitle properties
        registry.register(TalentTitleTitleChanged.class, "TalentTitleTitleChanged.Handler");
    }

}
