package io.bandit.limbo.limbo.modules.main.worktype;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.worktype.commands.*;
import io.bandit.limbo.limbo.modules.main.worktype.queries.*;
import io.bandit.limbo.limbo.modules.main.worktype.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkTypeModule {

    public WorkTypeModule(final CommandRegistry commandRegistry, 
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
        registry.register(WorkTypePersist.Command.class, "WorkTypePersist.CommandHandler");
        registry.register(WorkTypeDelete.Command.class, "WorkTypeDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetWorkType.Query.class, "GetWorkType.QueryHandler");
        registry.register(WorkTypeList.Query.class, "WorkTypeList.QueryHandler");
        registry.register(WorkTypePaginated.Query.class, "WorkTypePaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(WorkTypeCreated.class, "WorkTypeCreated.ReadModelHandler");
        registry.register(WorkTypeUpdated.class, "WorkTypeUpdated.ReadModelHandler");
        registry.register(WorkTypeDeleted.class, "WorkTypeDeleted.ReadModelHandler");

        //WorkType properties
        registry.register(WorkTypeWorkTypeChanged.class, "WorkTypeWorkTypeChanged.Handler");
        registry.register(WorkTypeDescriptionChanged.class, "WorkTypeDescriptionChanged.Handler");
    }

}
