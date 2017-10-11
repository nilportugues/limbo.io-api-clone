package io.bandit.limbo.limbo.modules.main.joboffer;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.modules.main.joboffer.commands.*;
import io.bandit.limbo.limbo.modules.main.joboffer.queries.*;
import io.bandit.limbo.limbo.modules.main.joboffer.event.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobOfferModule {

    public JobOfferModule(final CommandRegistry commandRegistry, 
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
        registry.register(JobOfferPersist.Command.class, "JobOfferPersist.CommandHandler");
        registry.register(JobOfferDelete.Command.class, "JobOfferDelete.CommandHandler");
        registry.register(JobOfferTalentPersist.Command.class, "JobOfferTalentPersist.CommandHandler");
        registry.register(JobOfferTalentDelete.Command.class, "JobOfferTalentDelete.CommandHandler");
    }

    /**
     * A Query must be mapped to a QueryHandler.
     */
    private void registerQueryHandlers(final QueryRegistry registry) {
        registry.register(GetJobOffer.Query.class, "GetJobOffer.QueryHandler");
        registry.register(JobOfferList.Query.class, "JobOfferList.QueryHandler");
        registry.register(JobOfferPaginated.Query.class, "JobOfferPaginated.QueryHandler");
        registry.register(JobOfferTalent.Query.class, "JobOfferTalent.QueryHandler");
        registry.register(JobOfferTalentPaginated.Query.class, "JobOfferTalentPaginated.QueryHandler");
    }

    /**
     * An Event may be registered to more than one Handler.
     *
     * It is good practive to have more than one ReadModel in over to cover all
     * your business needs. One just won't cut it.
     *
     */
    private void registerEventHandlers(final EventRegistry registry) {

        registry.register(JobOfferCreated.class, "JobOfferCreated.ReadModelHandler");
        registry.register(JobOfferUpdated.class, "JobOfferUpdated.ReadModelHandler");
        registry.register(JobOfferDeleted.class, "JobOfferDeleted.ReadModelHandler");

        //JobOffer properties
        registry.register(JobOfferTitleChanged.class, "JobOfferTitleChanged.Handler");
        registry.register(JobOfferDescriptionChanged.class, "JobOfferDescriptionChanged.Handler");
        registry.register(JobOfferSalaryMaxChanged.class, "JobOfferSalaryMaxChanged.Handler");
        registry.register(JobOfferSalaryMinChanged.class, "JobOfferSalaryMinChanged.Handler");
        registry.register(JobOfferSalaryCurrencyChanged.class, "JobOfferSalaryCurrencyChanged.Handler");

        //JobOffer's Talent events
        registry.register(JobOfferTalentChanged.class, "JobOfferTalentChanged.ReadModelHandler");
        registry.register(JobOfferTalentRemoved.class, "JobOfferTalentRemoved.ReadModelHandler");
            }

}
