package io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TalentExperience Model.
 */
@Entity
@Table(name = "talent_experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentExperienceJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "years")
    private String years;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        TalentExperienceJpaModel talentExperience = (TalentExperienceJpaModel) o;

        return !(talentExperience.id == null || id == null) && Objects.equals(id, talentExperience.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentExperienceJpaModel{" +
            "id=" + id +
            ", years='" + years + "'" +
            '}';
    }
}
