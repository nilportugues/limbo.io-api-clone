package io.bandit.limbo.limbo.modules.main.city.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.event.*;
import io.bandit.limbo.limbo.modules.main.city.event.CityNameChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A City Domain Entity.
 */
public class City extends Aggregate {

    private String id;

    private String name;
    private Country country;

    public static City create(String id, String name) {
        final City self = new City();
        self.id = id;
        self.name = name;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new CityImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (null != name && !name.equals(this.name)) {
            this.name = name;
            apply(new CityNameChanged(this));
        }
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        if (!Objects.equals(this.country, country)) {
            removeCountry();
            this.country = country;
            apply(new CityCountryChanged(this));
        }
    }

    public void removeCountry() {
        if (null != this.country) {
            apply(new CityCountryChanged(this));
            this.country = null;
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
        final City city = (City) o;

        return !(city.id == null || id == null) && Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "City{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
