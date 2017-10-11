package io.bandit.limbo.limbo.modules.main.companytraits.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.companytraits.event.*;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsTitleChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A CompanyTraits Domain Entity.
 */
public class CompanyTraits extends Aggregate {

    private String id;

    private String title;
    private Talent talent;

    public static CompanyTraits create(String id, String title) {
        final CompanyTraits self = new CompanyTraits();
        self.id = id;
        self.title = title;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new CompanyTraitsImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (null != title && !title.equals(this.title)) {
            this.title = title;
            apply(new CompanyTraitsTitleChanged(this));
        }
    }
    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        if (!Objects.equals(this.talent, talent)) {
            removeTalent();
            this.talent = talent;
            apply(new CompanyTraitsTalentChanged(this));
        }
    }

    public void removeTalent() {
        if (null != this.talent) {
            apply(new CompanyTraitsTalentChanged(this));
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
        final CompanyTraits companyTraits = (CompanyTraits) o;

        return !(companyTraits.id == null || id == null) && Objects.equals(id, companyTraits.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyTraits{" +
            "id=" + id +
            ", title='" + title + "'" +
            '}';
    }
}
