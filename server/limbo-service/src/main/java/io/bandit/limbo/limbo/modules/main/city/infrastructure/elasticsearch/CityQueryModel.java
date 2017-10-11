package io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A City Read Model.
 */
@Document(indexName = "v1_city", type = "city")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CityQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "country")
    private String countryId;

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
    public String getCountry() {
        return countryId;
    }

    public void setCountry(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CityQueryModel city = (CityQueryModel) o;

        return !(city.id == null || id == null) && Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CityQueryModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
