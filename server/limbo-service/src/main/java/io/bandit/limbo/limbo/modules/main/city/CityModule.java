package io.bandit.limbo.limbo.modules.main.city;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.city.commands.*;
import io.bandit.limbo.limbo.modules.main.city.queries.*;
import io.bandit.limbo.limbo.modules.main.city.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CityModule {

    public CityModule(final CommandRegistry commandRegistry, 
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
        registry.register(CityPersist.Command.class, "CityPersist.CommandHandler");
        registry.register(CityDelete.Command.class, "CityDelete.CommandHandler");
        registry.register(CityCountryPersist.Command.class, "CityCountryPersist.CommandHandler");
        registry.register(CityCountryDelete.Command.class, "CityCountryDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetCity.Query.class, "GetCity.QueryHandler");
        registry.register(CityList.Query.class, "CityList.QueryHandler");
        registry.register(CityPaginated.Query.class, "CityPaginated.QueryHandler");
        registry.register(CityCountry.Query.class, "CityCountry.QueryHandler");
        registry.register(CityCountryPaginated.Query.class, "CityCountryPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(CityCreated.class, "CityCreated.ReadModelHandler");
        registry.register(CityUpdated.class, "CityUpdated.ReadModelHandler");
        registry.register(CityDeleted.class, "CityDeleted.ReadModelHandler");

        //City properties
        registry.register(CityNameChanged.class, "CityNameChanged.Handler");

        //City's Country events
        registry.register(CityCountryChanged.class, "CityCountryChanged.ReadModelHandler");
        registry.register(CityCountryRemoved.class, "CityCountryRemoved.ReadModelHandler");
            }

}
