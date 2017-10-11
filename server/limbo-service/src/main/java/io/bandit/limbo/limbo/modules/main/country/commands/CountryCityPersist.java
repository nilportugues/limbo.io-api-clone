package io.bandit.limbo.limbo.modules.main.country.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityCreated;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CountryCityPersist {

    public static class Command implements ICommand {
        private String countryId;
        private City city;

        public Command(final String countryId,
                       final City city) {
            this.countryId = countryId;
            this.city = city;
        }

        public String getCountryId() {
            return countryId;
        }

        public City getCity() {
            return city;
        }
    }

    @Named("CountryCityPersist.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CountryRepository countryRepository;
        private final CountryPersisterService persister;

        @Inject
        public CommandHandler(final CountryRepository countryRepository,
                              final CountryPersisterService persister) {

            this.countryRepository = countryRepository;
            this.persister = persister;
        }

        /**
         * Persist a City for a given Countries.
         */
        public CompletableFuture<City> handle(final Command command) {

            return CompletableFuture.supplyAsync(() -> {
                try {
                    final Country country = countryRepository.findOneWithCities(command.getCountryId());
                    if (null == country) {
                        return null;
                    }

                    final City city = command.getCity();
                    country.addCity(city);
                    persister.persist(country);

                    return city;
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
