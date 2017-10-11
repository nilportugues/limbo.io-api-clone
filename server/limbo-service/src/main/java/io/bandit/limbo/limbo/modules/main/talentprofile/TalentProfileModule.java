package io.bandit.limbo.limbo.modules.main.talentprofile;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.talentprofile.commands.*;
import io.bandit.limbo.limbo.modules.main.talentprofile.queries.*;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TalentProfileModule {

    public TalentProfileModule(final CommandRegistry commandRegistry, 
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
        registry.register(TalentProfilePersist.Command.class, "TalentProfilePersist.CommandHandler");
        registry.register(TalentProfileDelete.Command.class, "TalentProfileDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetTalentProfile.Query.class, "GetTalentProfile.QueryHandler");
        registry.register(TalentProfileList.Query.class, "TalentProfileList.QueryHandler");
        registry.register(TalentProfilePaginated.Query.class, "TalentProfilePaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(TalentProfileCreated.class, "TalentProfileCreated.ReadModelHandler");
        registry.register(TalentProfileUpdated.class, "TalentProfileUpdated.ReadModelHandler");
        registry.register(TalentProfileDeleted.class, "TalentProfileDeleted.ReadModelHandler");

        //TalentProfile properties
        registry.register(TalentProfileFirstNameChanged.class, "TalentProfileFirstNameChanged.Handler");
        registry.register(TalentProfileLastNameChanged.class, "TalentProfileLastNameChanged.Handler");
    }

}
