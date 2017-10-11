package io.bandit.limbo.limbo.modules.main.talentexperience.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceYearsChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A TalentExperience Domain Entity.
 */
public class TalentExperience extends Aggregate {

    private String id;

    private String years;

    public static TalentExperience create(String id, String years) {
        final TalentExperience self = new TalentExperience();
        self.id = id;
        self.years = years;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new TalentExperienceImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getYears() {
        return years;
    }

    public void setYears(String years) {

        if (null != years && !years.equals(this.years)) {
            this.years = years;
            apply(new TalentExperienceYearsChanged(this));
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
        final TalentExperience talentExperience = (TalentExperience) o;

        return !(talentExperience.id == null || id == null) && Objects.equals(id, talentExperience.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentExperience{" +
            "id=" + id +
            ", years='" + years + "'" +
            '}';
    }
}
