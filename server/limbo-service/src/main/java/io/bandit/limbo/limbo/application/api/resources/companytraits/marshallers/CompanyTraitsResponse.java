package io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = CompanyTraits.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyTraitsResponse implements CompanyTraitsJsonResponse {

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
    private CompanyTraitsLinks links = new CompanyTraitsLinks();

    public CompanyTraitsResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    public static CompanyTraitsResponse fromCompanyTraits(final CompanyTraits companyTraits){
        return fromCompanyTraits(companyTraits, null, null);
    }

    public static CompanyTraitsResponse fromCompanyTraits(
            final CompanyTraits companyTraits,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(companyTraits).isPresent()) {
            final CompanyTraitsResponse response = new CompanyTraitsResponse();
            response.id = companyTraits.getId();
            response.title = companyTraits.getTitle();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CompanyTraitsLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public CompanyTraitsLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
