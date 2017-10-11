package io.bandit.limbo.limbo.application.api.resources.talentexperience.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = TalentExperience.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentExperienceResponse implements TalentExperienceJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "years", index = 2)
    @FilterProperty(filterName = "years", modelProperty="years")
    @ApiModelProperty(name = "years", required = true, position = 2)
    private String years;

    @JsonProperty(value = "_links", index = 3)
    @ApiModelProperty(name = "_links", readOnly = true, position = 3)
    private TalentExperienceLinks links = new TalentExperienceLinks();

    public TalentExperienceResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getYears() {
        return years;
    }

    public static TalentExperienceResponse fromTalentExperience(final TalentExperience talentExperience){
        return fromTalentExperience(talentExperience, null, null);
    }

    public static TalentExperienceResponse fromTalentExperience(
            final TalentExperience talentExperience,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(talentExperience).isPresent()) {
            final TalentExperienceResponse response = new TalentExperienceResponse();
            response.id = talentExperience.getId();
            response.years = talentExperience.getYears();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TalentExperienceLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public TalentExperienceLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
