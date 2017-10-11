package io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalTraitsData implements PersonalTraitsJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "description")
    @ApiModelProperty(name = "description", required = true, position = 1)
    private String description;

    public PersonalTraitsData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PersonalTraits toPersonalTraits(PersonalTraitsData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return PersonalTraits.create(data.getId(),
                data.getDescription());
        }

        return null;
    }
}
