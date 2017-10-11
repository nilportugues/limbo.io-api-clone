package io.bandit.limbo.limbo.modules.main.city.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityCreated;
import io.bandit.limbo.limbo.modules.main.city.event.CityDeleted;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.CityRepository;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.event.CountryDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CityCountryDeleteMany {

    public static class Command implements ICommand {
        private Iterable<String> cityIds = new ArrayList<>();

        public Command(final Iterable<String> cityIds) {

            if (Optional.ofNullable(cityIds).isPresent()) {
                this.cityIds = cityIds;
            }
        }

        public Iterable<String> getCityIds() {
            return cityIds;
        }
    }

    @Named("CityCountryDeleteMany.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {

        private final CityRepository cityRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CityRepository cityRepository, final EventBus eventBus) {
            this.cityRepository = cityRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Country value for a given set of Cities.
         */
        public CompletableFuture<List<City>> handle(final Command command) {
        //public Mono<List<City>> deleteCountries(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final List<City> list = new ArrayList<>();

                command.getCityIds().forEach(cityId -> {
                    final City city = cityRepository.findOneWithCountry(cityId);
                    cityRepository.delete(cityId);

                    final List<DomainEvent> events = city.pullEvents();
                    events.forEach(v -> eventBus.dispatch(v));

                    list.add(city);
                });

                return list;
            });
        }
    }
}
