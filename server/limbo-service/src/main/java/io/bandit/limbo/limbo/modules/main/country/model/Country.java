package io.bandit.limbo.limbo.modules.main.country.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.country.event.*;
import io.bandit.limbo.limbo.modules.main.country.event.CountryNameChanged;
import io.bandit.limbo.limbo.modules.main.country.event.CountryCountryCodeChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A Country Domain Entity.
 */
public class Country extends Aggregate {

    private String id;

    private String name;
    private String countryCode;
    private Set<City> cities = new HashSet<>();

    public static Country create(String id, String name, String countryCode) {
        final Country self = new Country();
        self.id = id;
        self.name = name;
        self.countryCode = countryCode;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new CountryImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (null != name && !name.equals(this.name)) {
            this.name = name;
            apply(new CountryNameChanged(this));
        }
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {

        if (null != countryCode && !countryCode.equals(this.countryCode)) {
            this.countryCode = countryCode;
            apply(new CountryCountryCodeChanged(this));
        }
    }

    public Set<City> getCities() {
        return cities;
    }

    public void addCity(City city) {
        if (!Optional.ofNullable(this.cities).isPresent()) {
            this.cities = new HashSet<>();
        }

        this.cities.add(city);
        city.setCountry(this);
        apply(new  CountryCityAdded(this, city));
    }

    public void removeCity(City city) {
        if (Optional.ofNullable(this.cities).isPresent() && this.cities.contains(city)) {
            this.cities.remove(city);
            apply(new CountryCityRemoved(this, city));
        }
    }

    public void setCities(Set<City> cities) {

        Set<City> input = cities;
        if (!Optional.ofNullable(cities).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.cities, null)) {
            this.cities = new HashSet<>();
        }

        if (!Objects.equals(this.cities, input)) {
            this.cities.forEach(v -> apply(new CountryCityRemoved(this, v)));

            this.cities.clear();
            this.cities.addAll(input);

            this.cities.forEach(v -> apply(new CountryCityAdded(this, v)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Country country = (Country) o;

        return !(country.id == null || id == null) && Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", countryCode='" + countryCode + "'" +
            '}';
    }
}
