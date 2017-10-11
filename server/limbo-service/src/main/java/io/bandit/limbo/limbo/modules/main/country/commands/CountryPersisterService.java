package io.bandit.limbo.limbo.modules.main.country.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.event.CountryCreated;
import io.bandit.limbo.limbo.modules.main.country.event.CountryUpdated;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class CountryPersisterService {

    private final CountryRepository countryRepository;
    private final EventBus eventBus;

    @Inject
    public CountryPersisterService(final CountryRepository countryRepository, final EventBus eventBus) {
        this.countryRepository = countryRepository;
        this.eventBus = eventBus;
    }

    public Country persist(final Country country) throws Throwable {

        final Country existing = countryRepository.findOne(country.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(country);
        }

        return update(country);
    }

    public Country create(final Country country) throws Throwable {

        countryRepository.save(country);
        eventBus.dispatch(new CountryCreated(country));

        return country;
    }

    public Country update(final Country data) throws Throwable {

        final Country existing = countryRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final Country country = updateData(data, existing);
        countryRepository.save(country);

        //Dispatch changed field events.
        final List<DomainEvent> events = country.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new CountryUpdated(country));

        return country;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for Country.
    * @param country   Country instance to copy data values to.
    * @return  An updated Country instance.
    */
    private Country updateData(final Country data, final Country country) {

        country.setName(data.getName());
        country.setCountryCode(data.getCountryCode());


        final Set<City> cities = data.getCities();
        if (Optional.ofNullable(cities).isPresent()) {
            cities.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            country.setCities(cities);
        }

        return country;
    }
}
