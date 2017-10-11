package io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TalentRole Read Model.
 */
@Document(indexName = "v1_talentrole", type = "talentrole")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentRoleQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

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

    @JsonIgnore
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TalentRoleQueryModel talentRole = (TalentRoleQueryModel) o;

        return !(talentRole.id == null || id == null) && Objects.equals(id, talentRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentRoleQueryModel{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
