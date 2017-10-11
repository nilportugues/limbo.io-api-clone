package io.bandit.limbo.limbo.application.api.resources.worktype.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkTypeData implements WorkTypeJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "work_type")
    @ApiModelProperty(name = "work_type", required = true, position = 1)
    private String workType;
    @JsonProperty(value = "description")
    @ApiModelProperty(name = "description", required = true, position = 2)
    private String description;

    public WorkTypeData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
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

    public static WorkType toWorkType(WorkTypeData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return WorkType.create(data.getId(),
                data.getWorkType(),
                data.getDescription());
        }

        return null;
    }
}
