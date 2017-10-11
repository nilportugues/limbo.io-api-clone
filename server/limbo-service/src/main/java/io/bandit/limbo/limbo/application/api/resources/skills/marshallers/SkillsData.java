package io.bandit.limbo.limbo.application.api.resources.skills.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillsData implements SkillsJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "skill")
    @ApiModelProperty(name = "skill", required = true, position = 1)
    private String skill;

    public SkillsData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public static Skills toSkills(SkillsData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return Skills.create(data.getId(),
                data.getSkill());
        }

        return null;
    }
}
