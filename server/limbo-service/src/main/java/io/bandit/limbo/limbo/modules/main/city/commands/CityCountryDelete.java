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

public class CityCountryDelete {

    public static class Command implements ICommand {
         private String cityId;

         public Command(final String cityId) {
             this.cityId = cityId;
         }

         public String getCityId() {
             return cityId;
        }
    }

    @Named("CityCountryDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CityRepository cityRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CityRepository cityRepository, final EventBus eventBus) {
            this.cityRepository = cityRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the current Country value for a given City.
         */
        public CompletableFuture<City> handle(final Command command) {
        //public Mono<City> deleteCountry(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                final City city = cityRepository.findOneWithCountry(command.getCityId());
                cityRepository.delete(command.getCityId());

                final List<DomainEvent> events = city.pullEvents();
                events.forEach(v -> eventBus.dispatch(v));

                return city;
            });
        }
    }
}
