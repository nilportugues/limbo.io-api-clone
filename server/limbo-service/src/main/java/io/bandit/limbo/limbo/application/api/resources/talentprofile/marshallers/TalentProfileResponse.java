package io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = TalentProfile.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentProfileResponse implements TalentProfileJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "first_name", index = 2)
    @FilterProperty(filterName = "first_name", modelProperty="firstName")
    @ApiModelProperty(name = "first_name", required = true, position = 2)
    private String firstName;

    @JsonProperty(value = "last_name", index = 3)
    @FilterProperty(filterName = "last_name", modelProperty="lastName")
    @ApiModelProperty(name = "last_name", required = true, position = 3)
    private String lastName;

    @JsonProperty(value = "_links", index = 4)
    @ApiModelProperty(name = "_links", readOnly = true, position = 4)
    private TalentProfileLinks links = new TalentProfileLinks();

    public TalentProfileResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    public String getLastName() {
        return lastName;
    }

    public static TalentProfileResponse fromTalentProfile(final TalentProfile talentProfile){
        return fromTalentProfile(talentProfile, null, null);
    }

    public static TalentProfileResponse fromTalentProfile(
            final TalentProfile talentProfile,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(talentProfile).isPresent()) {
            final TalentProfileResponse response = new TalentProfileResponse();
            response.id = talentProfile.getId();
            response.firstName = talentProfile.getFirstName();
            response.lastName = talentProfile.getLastName();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TalentProfileLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public TalentProfileLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
