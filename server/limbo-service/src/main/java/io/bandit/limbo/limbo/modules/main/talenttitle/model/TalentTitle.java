package io.bandit.limbo.limbo.modules.main.talenttitle.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleTitleChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A TalentTitle Domain Entity.
 */
public class TalentTitle extends Aggregate {

    private String id;

    private String title;

    public static TalentTitle create(String id, String title) {
        final TalentTitle self = new TalentTitle();
        self.id = id;
        self.title = title;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new TalentTitleImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (null != title && !title.equals(this.title)) {
            this.title = title;
            apply(new TalentTitleTitleChanged(this));
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
        final TalentTitle talentTitle = (TalentTitle) o;

        return !(talentTitle.id == null || id == null) && Objects.equals(id, talentTitle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentTitle{" +
            "id=" + id +
            ", title='" + title + "'" +
            '}';
    }
}
