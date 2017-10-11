package io.bandit.limbo.limbo.application.api.resources.talent.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentData implements TalentJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "email")
    @ApiModelProperty(name = "email", required = true, position = 1)
    private String email;
    @JsonProperty(value = "password")
    @ApiModelProperty(name = "password", required = true, position = 2)
    private String password;

    public TalentData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Talent toTalent(TalentData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return Talent.create(data.getId(),
                data.getEmail(),
                data.getPassword());
        }

        return null;
    }
}
