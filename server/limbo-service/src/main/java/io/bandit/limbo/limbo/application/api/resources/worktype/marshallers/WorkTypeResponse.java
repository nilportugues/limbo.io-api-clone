package io.bandit.limbo.limbo.application.api.resources.worktype.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = WorkType.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkTypeResponse implements WorkTypeJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "work_type", index = 2)
    @FilterProperty(filterName = "work_type", modelProperty="workType")
    @ApiModelProperty(name = "work_type", required = true, position = 2)
    private String workType;

    @JsonProperty(value = "description", index = 3)
    @FilterProperty(filterName = "description", modelProperty="description")
    @ApiModelProperty(name = "description", required = true, position = 3)
    private String description;

    @JsonProperty(value = "_links", index = 4)
    @ApiModelProperty(name = "_links", readOnly = true, position = 4)
    private WorkTypeLinks links = new WorkTypeLinks();

    public WorkTypeResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getWorkType() {
        return workType;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public static WorkTypeResponse fromWorkType(final WorkType workType){
        return fromWorkType(workType, null, null);
    }

    public static WorkTypeResponse fromWorkType(
            final WorkType workType,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(workType).isPresent()) {
            final WorkTypeResponse response = new WorkTypeResponse();
            response.id = workType.getId();
            response.workType = workType.getWorkType();
            response.description = workType.getDescription();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WorkTypeLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public WorkTypeLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
