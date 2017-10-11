package io.bandit.limbo.limbo.modules.main.worktype.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeWorkTypeChanged;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeDescriptionChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A WorkType Domain Entity.
 */
public class WorkType extends Aggregate {

    private String id;

    private String workType;
    private String description;

    public static WorkType create(String id, String workType, String description) {
        final WorkType self = new WorkType();
        self.id = id;
        self.workType = workType;
        self.description = description;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new WorkTypeImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {

        if (null != workType && !workType.equals(this.workType)) {
            this.workType = workType;
            apply(new WorkTypeWorkTypeChanged(this));
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (null != description && !description.equals(this.description)) {
            this.description = description;
            apply(new WorkTypeDescriptionChanged(this));
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
        final WorkType workType = (WorkType) o;

        return !(workType.id == null || id == null) && Objects.equals(id, workType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkType{" +
            "id=" + id +
            ", workType='" + workType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
