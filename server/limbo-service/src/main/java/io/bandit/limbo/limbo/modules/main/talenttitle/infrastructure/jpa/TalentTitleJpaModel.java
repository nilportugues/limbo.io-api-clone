package io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TalentTitle Model.
 */
@Entity
@Table(name = "talent_title")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentTitleJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "title")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TalentTitleJpaModel talentTitle = (TalentTitleJpaModel) o;

        return !(talentTitle.id == null || id == null) && Objects.equals(id, talentTitle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentTitleJpaModel{" +
            "id=" + id +
            ", title='" + title + "'" +
            '}';
    }
}
