package io.bandit.limbo.limbo.modules.main.country.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CountryCityDelete {

    public static class Command implements ICommand {
        private String countryId;

        public Command(final String countryId) {
            this.countryId = countryId;
        }

        public String getCountryId() {
            return countryId;
        }
    }

    @Named("CountryCityDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final CountryRepository countryRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CountryRepository countryRepository, final EventBus eventBus) {
            this.countryRepository = countryRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete all City for a given Countries.
         */
        public CompletableFuture<Country> handle(final Command command) {
        //public Country deleteAll(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final Country country = countryRepository.findOne(command.getCountryId());

                if (null != country) {
                    final List<DomainEvent> domainEvents = new ArrayList<>();
                    findCountryWithCities(country);
                    createDeleteEvents(domainEvents, country);
                    deleteAllCities(country);
                    raiseDeleteAllEvents(domainEvents);
                }
                return country;
            });
        }

        private void createDeleteEvents(final List<DomainEvent> domainEvents, final Country country) {
            country.getCities().forEach(v -> domainEvents.add(new CityDeleted(v)));
        }

        private void deleteAllCities(final Country country) {
            countryRepository.deleteAllCities(country.getId());
        }

        public void deleteAllCities(final String countryId) {

            final Country country = countryRepository.findOne(countryId);
            if (Optional.ofNullable(country).isPresent()) {
                countryRepository.deleteAllCities(countryId);
            }
        }

        private void raiseDeleteAllEvents(final List<DomainEvent> domainEvents) {
            domainEvents.forEach(eventBus::dispatch);
        }

        private Country findCountryWithCities(final Country country) {
            return countryRepository.findOneWithCities(country.getId());
        }
    }
}
