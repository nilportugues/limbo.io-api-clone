package io.bandit.limbo.limbo.application.api.resources.talentrole.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentRoleData implements TalentRoleJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "title")
    @ApiModelProperty(name = "title", required = true, position = 1)
    private String title;
    @JsonProperty(value = "description")
    @ApiModelProperty(name = "description", required = true, position = 2)
    private String description;

    public TalentRoleData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
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

    public static TalentRole toTalentRole(TalentRoleData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return TalentRole.create(data.getId(),
                data.getTitle(),
                data.getDescription());
        }

        return null;
    }
}
