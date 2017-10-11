package io.bandit.limbo.limbo.modules.main.country;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.country.commands.*;
import io.bandit.limbo.limbo.modules.main.country.queries.*;
import io.bandit.limbo.limbo.modules.main.country.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryModule {

    public CountryModule(final CommandRegistry commandRegistry, 
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
        registry.register(CountryPersist.Command.class, "CountryPersist.CommandHandler");
        registry.register(CountryDelete.Command.class, "CountryDelete.CommandHandler");
        registry.register(CountryCityPersist.Command.class, "CountryCityPersist.CommandHandler");
        registry.register(CountryCityDelete.Command.class, "CountryCityDelete.CommandHandler");
        registry.register(CountryCityDeleteMany.Command.class, "CountryCityDeleteMany.CommandHandler");
        //registry.register(CountryCityDeleteMany.Command.class, "CountryCityDeleteMany.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetCountry.Query.class, "GetCountry.QueryHandler");
        registry.register(CountryList.Query.class, "CountryList.QueryHandler");
        registry.register(CountryPaginated.Query.class, "CountryPaginated.QueryHandler");
        registry.register(CountryCity.Query.class, "CountryCity.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(CountryCreated.class, "CountryCreated.ReadModelHandler");
        registry.register(CountryUpdated.class, "CountryUpdated.ReadModelHandler");
        registry.register(CountryDeleted.class, "CountryDeleted.ReadModelHandler");

        //Country properties
        registry.register(CountryNameChanged.class, "CountryNameChanged.Handler");
        registry.register(CountryCountryCodeChanged.class, "CountryCountryCodeChanged.Handler");

        //Country's City events
        registry.register(CountryCityAdded.class, "CountryCityAdded.ReadModelHandler");
        registry.register(CountryCityRemoved.class, "CountryCityRemoved.ReadModelHandler");
    }

}
