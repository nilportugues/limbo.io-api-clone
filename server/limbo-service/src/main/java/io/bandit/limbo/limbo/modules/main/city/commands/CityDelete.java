package io.bandit.limbo.limbo.modules.main.city.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityDeleted;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.CityRepository;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.event.CountryDeleted;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class CityDelete {

    public static class Command implements ICommand {
         private String cityId;

         public Command(final String cityId) {
             this.cityId = cityId;
         }

         public String getCityId() {
             return cityId;
        }
    }

    @Named("CityDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final CityRepository cityRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final CityRepository cityRepository, final EventBus eventBus) {
            this.cityRepository = cityRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the City by id.
         */
        public CompletableFuture<City> handle(final Command command) {
        //public Mono<City> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final City city = cityRepository.findOne(command.getCityId());

                if (null != city) {
                    this.removeDomainRelationships(city);
                    this.removePersistenceRelationships(city);
                    this.raiseDeletedEvent(city);
                }

                return city;
            });
        }

        private void removeDomainRelationships(final City city) {
            city.setCountry(null);
        }

        private void removePersistenceRelationships(final City city) {
            cityRepository.save(city);
            cityRepository.delete(city.getId());
        }

        private void raiseDeletedEvent(final City city) {
            eventBus.dispatch(new CityDeleted(city));
        }
    }

}
