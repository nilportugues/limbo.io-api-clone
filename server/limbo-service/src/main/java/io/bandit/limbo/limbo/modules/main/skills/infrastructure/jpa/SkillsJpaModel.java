package io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Skills Model.
 */
@Entity
@Table(name = "skills")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillsJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "skill")
    private String skill;

    @ManyToOne(fetch = FetchType.LAZY)
    private TalentJpaModel talent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }


    public void setSkill(String skill) {
        this.skill = skill;
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
        SkillsJpaModel skills = (SkillsJpaModel) o;

        return !(skills.id == null || id == null) && Objects.equals(id, skills.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SkillsJpaModel{" +
            "id=" + id +
            ", skill='" + skill + "'" +
            '}';
    }
}
