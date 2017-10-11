package io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CityJpaSpecification {

   /**
    * @param countryId find by one Country id.
    */
    public static Specification<CityJpaModel> byCountrySpecification(final String countryId) {
        return (root, query, cb) -> {
            final Join<City, Country> cityForCountryJoin = root.join(
                "country",
                JoinType.LEFT
            );

            final CountryJpaModel country = new CountryJpaModel();
            country.setId(countryId);

            return cityForCountryJoin.in(country);
        };
    }

   /**
    * @param countryIds find by a list of Country ids.
    */
    public static Specification<CityJpaModel> byCountrySpecification(final Collection<String> countryIds) {
        return (root, query, cb) -> {
            final Join<City, Country> cityForCountryJoin = root.join(
                "country",
                JoinType.LEFT
            );

            final List<CountryJpaModel> list = new ArrayList<>();
            countryIds.forEach(v -> {
                final CountryJpaModel country = new CountryJpaModel();
                country.setId(v);
                list.add(country);
            });

            return cityForCountryJoin.in(list);
        };
    }

   /**
    * @param countries find by a list of CountryJpaModel instances.
    */
    public static Specification<CityJpaModel> byCountrySpecification(final Iterable<CountryJpaModel> countries) {
        return (root, query, cb) -> {
            final Join<City, Country> cityForCountryJoin = root.join(
                "country",
                JoinType.LEFT
            );

            return cityForCountryJoin.in(countries);
        };
    }
    
}
