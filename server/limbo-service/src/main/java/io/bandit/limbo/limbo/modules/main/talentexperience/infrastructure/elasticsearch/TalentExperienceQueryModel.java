package io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TalentExperience Read Model.
 */
@Document(indexName = "v1_talentexperience", type = "talentexperience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentExperienceQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "years")
    private String years;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getYears() {
        return years;
    }


    public void setYears(String years) {
        this.years = years;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TalentExperienceQueryModel talentExperience = (TalentExperienceQueryModel) o;

        return !(talentExperience.id == null || id == null) && Objects.equals(id, talentExperience.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentExperienceQueryModel{" +
            "id=" + id +
            ", years='" + years + "'" +
            '}';
    }
}
