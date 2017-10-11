package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonalTraits Model.
 */
@Entity
@Table(name = "personal_traits")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonalTraitsJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private TalentJpaModel talent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public TalentJpaModel getTalent() {
        return talent;
    }

    public void setTalent(TalentJpaModel talent) {
        this.talent = talent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonalTraitsJpaModel personalTraits = (PersonalTraitsJpaModel) o;

        return !(personalTraits.id == null || id == null) && Objects.equals(id, personalTraits.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonalTraitsJpaModel{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
