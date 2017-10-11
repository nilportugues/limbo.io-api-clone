package io.bandit.limbo.limbo.modules.main.country.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.main.city.event.CityDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CountryCityDeleteMany {

    public static class Command implements ICommand {
        private String countryId;
        private Iterable<String> cityIds = new ArrayList<>();

        public Command(final String countryId, final Iterable<String> cityIds) {
            this.countryId = countryId;

            if (Optional.ofNullable(cityIds).isPresent()) {
                this.cityIds = cityIds;
            }
        }

        public String getCountryId() {
            return countryId;
        }

        public Iterable<String> getCityIds() {
            return cityIds;
        }
    }

    @Named("CountryCityDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final CountryRepository countryRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CountryRepository countryRepository, final EventBus eventBus) {
            this.countryRepository = countryRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete list of City for a given Countries.
         */
        public CompletableFuture<Country> handle(final Command command) {
        //public Mono<Country> deleteAllCities(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<DomainEvent> domainEvents = new ArrayList<>();
                final List<String> ids = new ArrayList<>();

                final String id = command.getCountryId();
                final Country country = countryRepository.findOneWithCities(id);

                final Iterable<String> cityIds = command.getCityIds();
                cityIds.forEach(ids::add);

                country.getCities().forEach(v -> {
                    if (ids.contains(v.getId())) {
                        domainEvents.add(new CityDeleted(v));
                    }
                });

                countryRepository.deleteAllCities(id, cityIds);
                domainEvents.forEach(eventBus::dispatch);

                return country;
            });
        }
    }
}
