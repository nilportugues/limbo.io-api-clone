package io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A City Model.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CityJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private CountryJpaModel country;

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

    public CountryJpaModel getCountry() {
        return country;
    }

    public void setCountry(CountryJpaModel country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CityJpaModel city = (CityJpaModel) o;

        return !(city.id == null || id == null) && Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CityJpaModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
