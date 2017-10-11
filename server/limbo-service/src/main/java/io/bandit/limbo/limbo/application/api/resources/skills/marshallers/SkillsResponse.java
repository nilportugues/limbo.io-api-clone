package io.bandit.limbo.limbo.application.api.resources.skills.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = Skills.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillsResponse implements SkillsJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "skill", index = 2)
    @FilterProperty(filterName = "skill", modelProperty="skill")
    @ApiModelProperty(name = "skill", required = true, position = 2)
    private String skill;

    @JsonProperty(value = "_links", index = 3)
    @ApiModelProperty(name = "_links", readOnly = true, position = 3)
    private SkillsLinks links = new SkillsLinks();

    public SkillsResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getSkill() {
        return skill;
    }

    public static SkillsResponse fromSkills(final Skills skills){
        return fromSkills(skills, null, null);
    }

    public static SkillsResponse fromSkills(
            final Skills skills,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(skills).isPresent()) {
            final SkillsResponse response = new SkillsResponse();
            response.id = skills.getId();
            response.skill = skills.getSkill();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SkillsLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public SkillsLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
