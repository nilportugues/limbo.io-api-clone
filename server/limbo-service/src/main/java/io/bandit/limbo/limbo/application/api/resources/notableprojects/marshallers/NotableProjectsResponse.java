package io.bandit.limbo.limbo.application.api.resources.notableprojects.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = NotableProjects.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotableProjectsResponse implements NotableProjectsJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "title", index = 2)
    @FilterProperty(filterName = "title", modelProperty="title")
    @ApiModelProperty(name = "title", required = true, position = 2)
    private String title;

    @JsonProperty(value = "description", index = 3)
    @FilterProperty(filterName = "description", modelProperty="description")
    @ApiModelProperty(name = "description", required = true, position = 3)
    private String description;

    @JsonProperty(value = "_links", index = 4)
    @ApiModelProperty(name = "_links", readOnly = true, position = 4)
    private NotableProjectsLinks links = new NotableProjectsLinks();

    public NotableProjectsResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public static NotableProjectsResponse fromNotableProjects(final NotableProjects notableProjects){
        return fromNotableProjects(notableProjects, null, null);
    }

    public static NotableProjectsResponse fromNotableProjects(
            final NotableProjects notableProjects,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(notableProjects).isPresent()) {
            final NotableProjectsResponse response = new NotableProjectsResponse();
            response.id = notableProjects.getId();
            response.title = notableProjects.getTitle();
            response.description = notableProjects.getDescription();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NotableProjectsLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public NotableProjectsLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
