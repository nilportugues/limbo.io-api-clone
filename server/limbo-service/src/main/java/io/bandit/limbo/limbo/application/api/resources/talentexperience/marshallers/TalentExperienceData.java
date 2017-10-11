package io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentExperienceData implements TalentExperienceJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "years")
    @ApiModelProperty(name = "years", required = true, position = 1)
    private String years;

    public TalentExperienceData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public static TalentExperience toTalentExperience(TalentExperienceData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return TalentExperience.create(data.getId(),
                data.getYears());
        }

        return null;
    }
}
