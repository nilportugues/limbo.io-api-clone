package io.bandit.limbo.limbo.modules.main.companytraits;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.companytraits.commands.*;
import io.bandit.limbo.limbo.modules.main.companytraits.queries.*;
import io.bandit.limbo.limbo.modules.main.companytraits.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyTraitsModule {

    public CompanyTraitsModule(final CommandRegistry commandRegistry, 
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
        registry.register(CompanyTraitsPersist.Command.class, "CompanyTraitsPersist.CommandHandler");
        registry.register(CompanyTraitsDelete.Command.class, "CompanyTraitsDelete.CommandHandler");
        registry.register(CompanyTraitsTalentPersist.Command.class, "CompanyTraitsTalentPersist.CommandHandler");
        registry.register(CompanyTraitsTalentDelete.Command.class, "CompanyTraitsTalentDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetCompanyTraits.Query.class, "GetCompanyTraits.QueryHandler");
        registry.register(CompanyTraitsList.Query.class, "CompanyTraitsList.QueryHandler");
        registry.register(CompanyTraitsPaginated.Query.class, "CompanyTraitsPaginated.QueryHandler");
        registry.register(CompanyTraitsTalent.Query.class, "CompanyTraitsTalent.QueryHandler");
        registry.register(CompanyTraitsTalentPaginated.Query.class, "CompanyTraitsTalentPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(CompanyTraitsCreated.class, "CompanyTraitsCreated.ReadModelHandler");
        registry.register(CompanyTraitsUpdated.class, "CompanyTraitsUpdated.ReadModelHandler");
        registry.register(CompanyTraitsDeleted.class, "CompanyTraitsDeleted.ReadModelHandler");

        //CompanyTraits properties
        registry.register(CompanyTraitsTitleChanged.class, "CompanyTraitsTitleChanged.Handler");

        //CompanyTraits's Talent events
        registry.register(CompanyTraitsTalentChanged.class, "CompanyTraitsTalentChanged.ReadModelHandler");
        registry.register(CompanyTraitsTalentRemoved.class, "CompanyTraitsTalentRemoved.ReadModelHandler");
            }

}
