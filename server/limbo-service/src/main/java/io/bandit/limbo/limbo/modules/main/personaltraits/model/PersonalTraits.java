package io.bandit.limbo.limbo.modules.main.personaltraits.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.*;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsDescriptionChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A PersonalTraits Domain Entity.
 */
public class PersonalTraits extends Aggregate {

    private String id;

    private String description;
    private Talent talent;

    public static PersonalTraits create(String id, String description) {
        final PersonalTraits self = new PersonalTraits();
        self.id = id;
        self.description = description;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new PersonalTraitsImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (null != description && !description.equals(this.description)) {
            this.description = description;
            apply(new PersonalTraitsDescriptionChanged(this));
        }
    }
    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        if (!Objects.equals(this.talent, talent)) {
            removeTalent();
            this.talent = talent;
            apply(new PersonalTraitsTalentChanged(this));
        }
    }

    public void removeTalent() {
        if (null != this.talent) {
            apply(new PersonalTraitsTalentChanged(this));
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
        final PersonalTraits personalTraits = (PersonalTraits) o;

        return !(personalTraits.id == null || id == null) && Objects.equals(id, personalTraits.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonalTraits{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
