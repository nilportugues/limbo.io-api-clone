package io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = PersonalTraits.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalTraitsResponse implements PersonalTraitsJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "description", index = 2)
    @FilterProperty(filterName = "description", modelProperty="description")
    @ApiModelProperty(name = "description", required = true, position = 2)
    private String description;

    @JsonProperty(value = "_links", index = 3)
    @ApiModelProperty(name = "_links", readOnly = true, position = 3)
    private PersonalTraitsLinks links = new PersonalTraitsLinks();

    public PersonalTraitsResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public static PersonalTraitsResponse fromPersonalTraits(final PersonalTraits personalTraits){
        return fromPersonalTraits(personalTraits, null, null);
    }

    public static PersonalTraitsResponse fromPersonalTraits(
            final PersonalTraits personalTraits,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(personalTraits).isPresent()) {
            final PersonalTraitsResponse response = new PersonalTraitsResponse();
            response.id = personalTraits.getId();
            response.description = personalTraits.getDescription();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PersonalTraitsLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public PersonalTraitsLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
