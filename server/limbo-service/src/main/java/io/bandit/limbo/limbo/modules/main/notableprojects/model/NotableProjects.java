package io.bandit.limbo.limbo.modules.main.notableprojects.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.*;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsTitleChanged;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsDescriptionChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A NotableProjects Domain Entity.
 */
public class NotableProjects extends Aggregate {

    private String id;

    private String title;
    private String description;
    private Talent talent;

    public static NotableProjects create(String id, String title, String description) {
        final NotableProjects self = new NotableProjects();
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
            throw new NotableProjectsImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (null != title && !title.equals(this.title)) {
            this.title = title;
            apply(new NotableProjectsTitleChanged(this));
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (null != description && !description.equals(this.description)) {
            this.description = description;
            apply(new NotableProjectsDescriptionChanged(this));
        }
    }
    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        if (!Objects.equals(this.talent, talent)) {
            removeTalent();
            this.talent = talent;
            apply(new NotableProjectsTalentChanged(this));
        }
    }

    public void removeTalent() {
        if (null != this.talent) {
            apply(new NotableProjectsTalentChanged(this));
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
        final NotableProjects notableProjects = (NotableProjects) o;

        return !(notableProjects.id == null || id == null) && Objects.equals(id, notableProjects.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NotableProjects{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
