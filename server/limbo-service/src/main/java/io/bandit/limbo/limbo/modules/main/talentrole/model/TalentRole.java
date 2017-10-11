package io.bandit.limbo.limbo.modules.main.talentrole.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleTitleChanged;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleDescriptionChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A TalentRole Domain Entity.
 */
public class TalentRole extends Aggregate {

    private String id;

    private String title;
    private String description;

    public static TalentRole create(String id, String title, String description) {
        final TalentRole self = new TalentRole();
        self.id = id;
        self.title = title;
        self.description = description;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new TalentRoleImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (null != title && !title.equals(this.title)) {
            this.title = title;
            apply(new TalentRoleTitleChanged(this));
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (null != description && !description.equals(this.description)) {
            this.description = description;
            apply(new TalentRoleDescriptionChanged(this));
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
        final TalentRole talentRole = (TalentRole) o;

        return !(talentRole.id == null || id == null) && Objects.equals(id, talentRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentRole{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
