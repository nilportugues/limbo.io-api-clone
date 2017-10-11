package io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WorkType Read Model.
 */
@Document(indexName = "v1_worktype", type = "worktype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkTypeQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "work_type")
    private String workType;

    @JsonProperty(value = "description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getWorkType() {
        return workType;
    }


    public void setWorkType(String workType) {
        this.workType = workType;
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
        WorkTypeQueryModel workType = (WorkTypeQueryModel) o;

        return !(workType.id == null || id == null) && Objects.equals(id, workType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkTypeQueryModel{" +
            "id=" + id +
            ", workType='" + workType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
