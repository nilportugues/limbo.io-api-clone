package io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TalentTitle Read Model.
 */
@Document(indexName = "v1_talenttitle", type = "talenttitle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentTitleQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "title")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
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
        TalentTitleQueryModel talentTitle = (TalentTitleQueryModel) o;

        return !(talentTitle.id == null || id == null) && Objects.equals(id, talentTitle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentTitleQueryModel{" +
            "id=" + id +
            ", title='" + title + "'" +
            '}';
    }
}
