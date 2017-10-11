package io.bandit.limbo.limbo.application.api.resources.talent.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = Talent.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentResponse implements TalentJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "email", index = 2)
    @FilterProperty(filterName = "email", modelProperty="email")
    @ApiModelProperty(name = "email", required = true, position = 2)
    private String email;

    @JsonProperty(value = "password", index = 3)
    @FilterProperty(filterName = "password", modelProperty="password")
    @ApiModelProperty(name = "password", required = true, position = 3)
    private String password;

    @JsonProperty(value = "_links", index = 4)
    @ApiModelProperty(name = "_links", readOnly = true, position = 4)
    private TalentLinks links = new TalentLinks();

    public TalentResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public static TalentResponse fromTalent(final Talent talent){
        return fromTalent(talent, null, null);
    }

    public static TalentResponse fromTalent(
            final Talent talent,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(talent).isPresent()) {
            final TalentResponse response = new TalentResponse();
            response.id = talent.getId();
            response.email = talent.getEmail();
            response.password = talent.getPassword();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TalentLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public TalentLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
