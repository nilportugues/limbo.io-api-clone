package io.bandit.limbo.limbo.modules.main.talentprofile.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileFirstNameChanged;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileLastNameChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A TalentProfile Domain Entity.
 */
public class TalentProfile extends Aggregate {

    private String id;

    private String firstName;
    private String lastName;

    public static TalentProfile create(String id, String firstName, String lastName) {
        final TalentProfile self = new TalentProfile();
        self.id = id;
        self.firstName = firstName;
        self.lastName = lastName;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new TalentProfileImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        if (null != firstName && !firstName.equals(this.firstName)) {
            this.firstName = firstName;
            apply(new TalentProfileFirstNameChanged(this));
        }
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        if (null != lastName && !lastName.equals(this.lastName)) {
            this.lastName = lastName;
            apply(new TalentProfileLastNameChanged(this));
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
        final TalentProfile talentProfile = (TalentProfile) o;

        return !(talentProfile.id == null || id == null) && Objects.equals(id, talentProfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentProfile{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            '}';
    }
}
