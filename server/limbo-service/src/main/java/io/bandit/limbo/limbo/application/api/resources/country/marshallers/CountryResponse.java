package io.bandit.limbo.limbo.application.api.resources.country.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = Country.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryResponse implements CountryJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "name", index = 2)
    @FilterProperty(filterName = "name", modelProperty="name")
    @ApiModelProperty(name = "name", required = true, position = 2)
    private String name;

    @JsonProperty(value = "country_code", index = 3)
    @FilterProperty(filterName = "country_code", modelProperty="countryCode")
    @ApiModelProperty(name = "country_code", required = true, position = 3)
    private String countryCode;

    @JsonProperty(value = "_links", index = 4)
    @ApiModelProperty(name = "_links", readOnly = true, position = 4)
    private CountryLinks links = new CountryLinks();

    public CountryResponse() {

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
    public String getCountryCode() {
        return countryCode;
    }

    public static CountryResponse fromCountry(final Country country){
        return fromCountry(country, null, null);
    }

    public static CountryResponse fromCountry(
            final Country country,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(country).isPresent()) {
            final CountryResponse response = new CountryResponse();
            response.id = country.getId();
            response.name = country.getName();
            response.countryCode = country.getCountryCode();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CountryLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public CountryLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
