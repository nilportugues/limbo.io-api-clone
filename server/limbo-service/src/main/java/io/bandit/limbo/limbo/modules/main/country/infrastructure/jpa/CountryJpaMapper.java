package io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaRepository;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaMapper;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Named("CountryJpaMapper")
public class CountryJpaMapper {

    @Inject private CountryJpaRepository countryJpaRepository;
    @Inject private CityJpaRepository cityJpaRepository;
    @Inject private CityJpaMapper cityJpaMapper;

    public Country toDomain(final CountryJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final Country country = new Country();

                country.setId(model.getId());
                country.setName(model.getName());
                country.setCountryCode(model.getCountryCode());
                setCityDomain(country, model);

                return country;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Converts City model data to domain data.
     *
     * @param country  The domain class representation for Country.
     * @param model  The model class representation for Country.
     */
    private void setCityDomain(final Country country, CountryJpaModel model) {
        country.setCities(model.getCities()
             .stream()
             .map(cityJpaMapper::toDomain)
             .collect(Collectors.toSet())
        );
    }

    public CountryJpaModel toModel(final Country country) {

        if (Optional.ofNullable(country).isPresent()) {
            try {
                CountryJpaModel model = new CountryJpaModel();
                if (Optional.ofNullable(country.getId()).isPresent()) {
                    final CountryJpaModel dbModel = countryJpaRepository.findOne(country.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(country.getId());
                model.setName(country.getName());
                model.setCountryCode(country.getCountryCode());

                oneToManyCityRelationship(country, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to many relationship between Country and City.
    *
    * @param country  The domain class representation for Country.
    * @param model  The model class representation for Country.
    */
    private void oneToManyCityRelationship(final Country country, final CountryJpaModel model) {

        final Optional<Set<City>> optional = Optional.ofNullable(country.getCities());
        if (!optional.isPresent()) {
            return;
        }

        final Stream<City> cities = country.getCities().stream();
        cities.forEach(v -> model.addCity(cityJpaMapper.toModel(v)));

        if (Optional.ofNullable(model.getCities()).isPresent()) {
            cityJpaRepository.save(model.getCities());
        }
    }

}
//isManyToMany: 
//isManyToOne: 
//isOneToOne: 
//isAlone: 