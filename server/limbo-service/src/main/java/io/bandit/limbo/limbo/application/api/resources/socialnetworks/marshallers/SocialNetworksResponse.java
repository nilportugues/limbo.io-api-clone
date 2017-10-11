package io.bandit.limbo.limbo.application.api.resources.socialnetworks.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = SocialNetworks.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialNetworksResponse implements SocialNetworksJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "name", index = 2)
    @FilterProperty(filterName = "name", modelProperty="name")
    @ApiModelProperty(name = "name", required = true, position = 2)
    private String name;

    @JsonProperty(value = "url", index = 3)
    @FilterProperty(filterName = "url", modelProperty="url")
    @ApiModelProperty(name = "url", required = true, position = 3)
    private String url;

    @JsonProperty(value = "_links", index = 4)
    @ApiModelProperty(name = "_links", readOnly = true, position = 4)
    private SocialNetworksLinks links = new SocialNetworksLinks();

    public SocialNetworksResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public static SocialNetworksResponse fromSocialNetworks(final SocialNetworks socialNetworks){
        return fromSocialNetworks(socialNetworks, null, null);
    }

    public static SocialNetworksResponse fromSocialNetworks(
            final SocialNetworks socialNetworks,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(socialNetworks).isPresent()) {
            final SocialNetworksResponse response = new SocialNetworksResponse();
            response.id = socialNetworks.getId();
            response.name = socialNetworks.getName();
            response.url = socialNetworks.getUrl();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SocialNetworksLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public SocialNetworksLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
