package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonalTraits Read Model.
 */
@Document(indexName = "v1_personaltraits", type = "personaltraits")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonalTraitsQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "talent")
    private String talentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    public String getTalent() {
        return talentId;
    }

    public void setTalent(String talentId) {
        this.talentId = talentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonalTraitsQueryModel personalTraits = (PersonalTraitsQueryModel) o;

        return !(personalTraits.id == null || id == null) && Objects.equals(id, personalTraits.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonalTraitsQueryModel{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
