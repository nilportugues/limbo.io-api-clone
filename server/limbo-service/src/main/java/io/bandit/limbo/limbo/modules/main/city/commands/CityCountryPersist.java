package io.bandit.limbo.limbo.modules.main.city.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityCreated;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.CityRepository;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.event.CountryCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CityCountryPersist {

    public static class Command implements ICommand {
        private String cityId;
        private Country country;

        public Command(final String cityId, final Country country) {
            this.cityId = cityId;
            this.country = country;
        }

        public String getCityId() {
             return cityId;
        }

        public Country getCountry() {
            return country;
        }
    }

    @Named("CityCountryPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CityRepository cityRepository;
        private final CityPersisterService persister;

        @Inject
        public CommandHandler(final CityRepository cityRepository,
                              final CityPersisterService persister) {

            this.cityRepository = cityRepository;
            this.persister = persister;
        }

        /**
         * Persist the current Country value for a given City.
         */
        public CompletableFuture<City> handle(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    final City city = cityRepository.findOne(command.getCityId());
                    if (null == city){
                        return null;
                    }

                    city.setCountry(command.getCountry());

                    return persister.persist(city);
                } catch (Throwable throwable) {
                    throwAsyncException(throwable);
                    return null;
                }
            });
        }

        private void throwAsyncException(final Throwable throwable) {
            final Thread thread = Thread.currentThread();
            thread.getUncaughtExceptionHandler().uncaughtException(thread, throwable);
        }
    }
}
