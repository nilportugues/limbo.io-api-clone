package io.bandit.limbo.limbo.modules.main.skills.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.skills.event.*;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsSkillChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A Skills Domain Entity.
 */
public class Skills extends Aggregate {

    private String id;

    private String skill;
    private Talent talent;

    public static Skills create(String id, String skill) {
        final Skills self = new Skills();
        self.id = id;
        self.skill = skill;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new SkillsImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {

        if (null != skill && !skill.equals(this.skill)) {
            this.skill = skill;
            apply(new SkillsSkillChanged(this));
        }
    }
    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        if (!Objects.equals(this.talent, talent)) {
            removeTalent();
            this.talent = talent;
            apply(new SkillsTalentChanged(this));
        }
    }

    public void removeTalent() {
        if (null != this.talent) {
            apply(new SkillsTalentChanged(this));
            this.talent = null;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Skills skills = (Skills) o;

        return !(skills.id == null || id == null) && Objects.equals(id, skills.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Skills{" +
            "id=" + id +
            ", skill='" + skill + "'" +
            '}';
    }
}
