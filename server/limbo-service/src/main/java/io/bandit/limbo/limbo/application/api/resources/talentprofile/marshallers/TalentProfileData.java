package io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentProfileData implements TalentProfileJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "first_name")
    @ApiModelProperty(name = "first_name", required = true, position = 1)
    private String firstName;
    @JsonProperty(value = "last_name")
    @ApiModelProperty(name = "last_name", required = true, position = 2)
    private String lastName;

    public TalentProfileData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonIgnore
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static TalentProfile toTalentProfile(TalentProfileData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return TalentProfile.create(data.getId(),
                data.getFirstName(),
                data.getLastName());
        }

        return null;
    }
}
