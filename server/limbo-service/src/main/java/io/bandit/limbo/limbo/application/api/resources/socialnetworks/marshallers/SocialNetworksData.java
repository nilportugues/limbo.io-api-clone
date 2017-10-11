package io.bandit.limbo.limbo.application.api.resources.socialnetworks.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialNetworksData implements SocialNetworksJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "name")
    @ApiModelProperty(name = "name", required = true, position = 1)
    private String name;
    @JsonProperty(value = "url")
    @ApiModelProperty(name = "url", required = true, position = 2)
    private String url;

    public SocialNetworksData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static SocialNetworks toSocialNetworks(SocialNetworksData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return SocialNetworks.create(data.getId(),
                data.getName(),
                data.getUrl());
        }

        return null;
    }
}
