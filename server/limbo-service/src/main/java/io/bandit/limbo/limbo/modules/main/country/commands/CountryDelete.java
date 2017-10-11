package io.bandit.limbo.limbo.modules.main.country.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.event.CountryDeleted;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class CountryDelete {

    public static class Command implements ICommand {
         private String countryId;

         public Command(final String countryId) {
             this.countryId = countryId;
         }

         public String getCountryId() {
             return countryId;
        }
    }

    @Named("CountryDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CountryRepository countryRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CountryRepository countryRepository, final EventBus eventBus) {
            this.countryRepository = countryRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the Country by id.
         */
        public CompletableFuture<Country> handle(final Command command) {
        //public Mono<Country> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final Country country = countryRepository.findOne(command.getCountryId());

                if (null != country) {
                    this.removeDomainRelationships(country);
                    this.removePersistenceRelationships(country);
                    this.raiseDeletedEvent(country);
                }

                return country;
            });
        }

        private void removeDomainRelationships(final Country country) {
            country.setCities(null);
        }

        private void removePersistenceRelationships(final Country country) {
            countryRepository.save(country);
            countryRepository.delete(country.getId());
        }

        private void raiseDeletedEvent(final Country country) {
            eventBus.dispatch(new CountryDeleted(country));
        }
    }

}
