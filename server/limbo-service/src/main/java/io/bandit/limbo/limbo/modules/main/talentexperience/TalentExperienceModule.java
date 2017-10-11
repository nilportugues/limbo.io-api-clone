package io.bandit.limbo.limbo.modules.main.talentexperience;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.talentexperience.commands.*;
import io.bandit.limbo.limbo.modules.main.talentexperience.queries.*;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TalentExperienceModule {

    public TalentExperienceModule(final CommandRegistry commandRegistry, 
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
        registry.register(TalentExperiencePersist.Command.class, "TalentExperiencePersist.CommandHandler");
        registry.register(TalentExperienceDelete.Command.class, "TalentExperienceDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetTalentExperience.Query.class, "GetTalentExperience.QueryHandler");
        registry.register(TalentExperienceList.Query.class, "TalentExperienceList.QueryHandler");
        registry.register(TalentExperiencePaginated.Query.class, "TalentExperiencePaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(TalentExperienceCreated.class, "TalentExperienceCreated.ReadModelHandler");
        registry.register(TalentExperienceUpdated.class, "TalentExperienceUpdated.ReadModelHandler");
        registry.register(TalentExperienceDeleted.class, "TalentExperienceDeleted.ReadModelHandler");

        //TalentExperience properties
        registry.register(TalentExperienceYearsChanged.class, "TalentExperienceYearsChanged.Handler");
    }

}
