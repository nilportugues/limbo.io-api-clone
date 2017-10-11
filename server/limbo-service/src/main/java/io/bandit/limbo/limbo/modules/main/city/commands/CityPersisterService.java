package io.bandit.limbo.limbo.modules.main.city.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.event.CityCreated;
import io.bandit.limbo.limbo.modules.main.city.event.CityUpdated;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.CityRepository;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class CityPersisterService {

    private final CityRepository cityRepository;
    private final EventBus eventBus;

    @Inject
    public CityPersisterService(final CityRepository cityRepository, final EventBus eventBus) {
        this.cityRepository = cityRepository;
        this.eventBus = eventBus;
    }

    public City persist(final City city) throws Throwable {

        final City existing = cityRepository.findOne(city.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(city);
        }

        return update(city);
    }

    public City create(final City city) throws Throwable {

        cityRepository.save(city);
        eventBus.dispatch(new CityCreated(city));

        return city;
    }

    public City update(final City data) throws Throwable {

        final City existing = cityRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final City city = updateData(data, existing);
        cityRepository.save(city);

        //Dispatch changed field events.
        final List<DomainEvent> events = city.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new CityUpdated(city));

        return city;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for City.
    * @param city   City instance to copy data values to.
    * @return  An updated City instance.
    */
    private City updateData(final City data, final City city) {

        city.setName(data.getName());


        final Country country = data.getCountry();
        if (Optional.ofNullable(country).isPresent()) {
            if (!Optional.ofNullable(country.getId()).isPresent()) {
                  try {
                    country.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            city.setCountry(country);
        }


        return city;
    }
}
