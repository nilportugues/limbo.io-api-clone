package io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Country Model.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CountryJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "country_code")
    private String countryCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CityJpaModel> cities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }


    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    public Set<CityJpaModel> getCities() {
        return cities;
    }

    public void addCity(CityJpaModel city) {
        this.cities.add(city);
        city.setCountry(this);
    }

    public void removeCity(CityJpaModel city) {
        this.cities.remove(city);
        city.setCountry(null);
    }

    public void setCities(Set<CityJpaModel> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryJpaModel country = (CountryJpaModel) o;

        return !(country.id == null || id == null) && Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CountryJpaModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", countryCode='" + countryCode + "'" +
            '}';
    }
}
