package io.bandit.limbo.limbo.application.api.resources.talenttitle.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = TalentTitle.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentTitleResponse implements TalentTitleJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "title", index = 2)
    @FilterProperty(filterName = "title", modelProperty="title")
    @ApiModelProperty(name = "title", required = true, position = 2)
    private String title;

    @JsonProperty(value = "_links", index = 3)
    @ApiModelProperty(name = "_links", readOnly = true, position = 3)
    private TalentTitleLinks links = new TalentTitleLinks();

    public TalentTitleResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    public static TalentTitleResponse fromTalentTitle(final TalentTitle talentTitle){
        return fromTalentTitle(talentTitle, null, null);
    }

    public static TalentTitleResponse fromTalentTitle(
            final TalentTitle talentTitle,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(talentTitle).isPresent()) {
            final TalentTitleResponse response = new TalentTitleResponse();
            response.id = talentTitle.getId();
            response.title = talentTitle.getTitle();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TalentTitleLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public TalentTitleLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
