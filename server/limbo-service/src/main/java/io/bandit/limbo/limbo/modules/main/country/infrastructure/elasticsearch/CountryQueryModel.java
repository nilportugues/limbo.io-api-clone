package io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Country Read Model.
 */
@Document(indexName = "v1_country", type = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CountryQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "country_code")
    private String countryCode;

    @JsonProperty(value = "cities")
    private Set<String> citiesIds = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getCountryCode() {
        return countryCode;
    }


    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonIgnore
    public Set<String> getCities() {
        return citiesIds;
    }

    public void addCity(String cityId) {
        this.citiesIds.add(cityId);
    }

    public void removeCity(String cityId) {
        this.citiesIds.remove(cityId);
    }

    public void setCities(Set<String> citiesIds) {
        this.citiesIds = citiesIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryQueryModel country = (CountryQueryModel) o;

        return !(country.id == null || id == null) && Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CountryQueryModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", countryCode='" + countryCode + "'" +
            '}';
    }
}
