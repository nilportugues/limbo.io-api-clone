package io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaRepository;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaMapper;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaRepository;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaMapper;

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
@Named("CityJpaMapper")
public class CityJpaMapper {

    @Inject private CityJpaRepository cityJpaRepository;
    @Inject private CountryJpaRepository countryJpaRepository;
    @Inject private CountryJpaMapper countryJpaMapper;

    public City toDomain(final CityJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final City city = new City();

                city.setId(model.getId());
                city.setName(model.getName());

                return city;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public CityJpaModel toModel(final City city) {

        if (Optional.ofNullable(city).isPresent()) {
            try {
                CityJpaModel model = new CityJpaModel();
                if (Optional.ofNullable(city.getId()).isPresent()) {
                    final CityJpaModel dbModel = cityJpaRepository.findOne(city.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(city.getId());
                model.setName(city.getName());

                manyToOneCountryRelationship(city, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between City and Country.
    *
    * @param city  The domain class representation for City.
    * @param model  The model class representation for City.
    */
    private void manyToOneCountryRelationship(final City city, final CityJpaModel model) {

        if (Optional.ofNullable(city.getCountry()).isPresent()) {
            final Country country = city.getCountry();
            final String id = country.getId();

            CountryJpaModel countryModel = null;
            if (Optional.ofNullable(country.getId()).isPresent()) {
                countryModel = countryJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getCountry(), countryModel)
                && Optional.ofNullable(model.getCountry()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(countryModel).isPresent()) {
                countryModel = countryJpaMapper.toModel(country);
            }

            if (null != model.getCountry()) {
                countryJpaRepository.delete(model.getCountry());
            }

            model.setCountry(countryModel);
        }
    }

}
//isManyToMany: 
//isManyToOne: true
//isOneToOne: 
//isAlone: 