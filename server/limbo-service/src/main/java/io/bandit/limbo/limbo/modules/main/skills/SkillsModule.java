package io.bandit.limbo.limbo.modules.main.skills;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.skills.commands.*;
import io.bandit.limbo.limbo.modules.main.skills.queries.*;
import io.bandit.limbo.limbo.modules.main.skills.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkillsModule {

    public SkillsModule(final CommandRegistry commandRegistry, 
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
        registry.register(SkillsPersist.Command.class, "SkillsPersist.CommandHandler");
        registry.register(SkillsDelete.Command.class, "SkillsDelete.CommandHandler");
        registry.register(SkillsTalentPersist.Command.class, "SkillsTalentPersist.CommandHandler");
        registry.register(SkillsTalentDelete.Command.class, "SkillsTalentDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetSkills.Query.class, "GetSkills.QueryHandler");
        registry.register(SkillsList.Query.class, "SkillsList.QueryHandler");
        registry.register(SkillsPaginated.Query.class, "SkillsPaginated.QueryHandler");
        registry.register(SkillsTalent.Query.class, "SkillsTalent.QueryHandler");
        registry.register(SkillsTalentPaginated.Query.class, "SkillsTalentPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(SkillsCreated.class, "SkillsCreated.ReadModelHandler");
        registry.register(SkillsUpdated.class, "SkillsUpdated.ReadModelHandler");
        registry.register(SkillsDeleted.class, "SkillsDeleted.ReadModelHandler");

        //Skills properties
        registry.register(SkillsSkillChanged.class, "SkillsSkillChanged.Handler");

        //Skills's Talent events
        registry.register(SkillsTalentChanged.class, "SkillsTalentChanged.ReadModelHandler");
        registry.register(SkillsTalentRemoved.class, "SkillsTalentRemoved.ReadModelHandler");
            }

}
