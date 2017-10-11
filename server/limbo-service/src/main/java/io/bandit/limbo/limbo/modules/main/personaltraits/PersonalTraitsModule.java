package io.bandit.limbo.limbo.modules.main.personaltraits;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.personaltraits.commands.*;
import io.bandit.limbo.limbo.modules.main.personaltraits.queries.*;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonalTraitsModule {

    public PersonalTraitsModule(final CommandRegistry commandRegistry, 
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
        registry.register(PersonalTraitsPersist.Command.class, "PersonalTraitsPersist.CommandHandler");
        registry.register(PersonalTraitsDelete.Command.class, "PersonalTraitsDelete.CommandHandler");
        registry.register(PersonalTraitsTalentPersist.Command.class, "PersonalTraitsTalentPersist.CommandHandler");
        registry.register(PersonalTraitsTalentDelete.Command.class, "PersonalTraitsTalentDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetPersonalTraits.Query.class, "GetPersonalTraits.QueryHandler");
        registry.register(PersonalTraitsList.Query.class, "PersonalTraitsList.QueryHandler");
        registry.register(PersonalTraitsPaginated.Query.class, "PersonalTraitsPaginated.QueryHandler");
        registry.register(PersonalTraitsTalent.Query.class, "PersonalTraitsTalent.QueryHandler");
        registry.register(PersonalTraitsTalentPaginated.Query.class, "PersonalTraitsTalentPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(PersonalTraitsCreated.class, "PersonalTraitsCreated.ReadModelHandler");
        registry.register(PersonalTraitsUpdated.class, "PersonalTraitsUpdated.ReadModelHandler");
        registry.register(PersonalTraitsDeleted.class, "PersonalTraitsDeleted.ReadModelHandler");

        //PersonalTraits properties
        registry.register(PersonalTraitsDescriptionChanged.class, "PersonalTraitsDescriptionChanged.Handler");

        //PersonalTraits's Talent events
        registry.register(PersonalTraitsTalentChanged.class, "PersonalTraitsTalentChanged.ReadModelHandler");
        registry.register(PersonalTraitsTalentRemoved.class, "PersonalTraitsTalentRemoved.ReadModelHandler");
            }

}
