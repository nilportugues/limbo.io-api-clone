package io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Skills Read Model.
 */
@Document(indexName = "v1_skills", type = "skills")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillsQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "skill")
    private String skill;

    @JsonProperty(value = "talent")
    private String talentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getSkill() {
        return skill;
    }


    public void setSkill(String skill) {
        this.skill = skill;
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
        SkillsQueryModel skills = (SkillsQueryModel) o;

        return !(skills.id == null || id == null) && Objects.equals(id, skills.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SkillsQueryModel{" +
            "id=" + id +
            ", skill='" + skill + "'" +
            '}';
    }
}
