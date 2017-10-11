package io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CountryJpaSpecification {

   /**
    * @param cityId find by one City id.
    */
    public static Specification<CountryJpaModel> byCitySpecification(final String cityId) {
        return (root, query, cb) -> {
            final Join<Country, City> countryForCityJoin = root.join(
                "city",
                JoinType.LEFT
            );

            final CityJpaModel city = new CityJpaModel();
            city.setId(cityId);

            return countryForCityJoin.in(city);
        };
    }

   /**
    * @param cityIds find by a list of City ids.
    */
    public static Specification<CountryJpaModel> byCitySpecification(final Collection<String> cityIds) {
        return (root, query, cb) -> {
            final Join<Country, City> countryForCityJoin = root.join(
                "city",
                JoinType.LEFT
            );

            final List<CityJpaModel> list = new ArrayList<>();
            cityIds.forEach(v -> {
                final CityJpaModel city = new CityJpaModel();
                city.setId(v);
                list.add(city);
            });

            return countryForCityJoin.in(list);
        };
    }

   /**
    * @param cities find by a list of CityJpaModel instances.
    */
    public static Specification<CountryJpaModel> byCitySpecification(final Iterable<CityJpaModel> cities) {
        return (root, query, cb) -> {
            final Join<Country, City> countryForCityJoin = root.join(
                "city",
                JoinType.LEFT
            );

            return countryForCityJoin.in(cities);
        };
    }
    
}
