package io.bandit.limbo.limbo.application.api.resources.city.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = City.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityResponse implements CityJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "name", index = 2)
    @FilterProperty(filterName = "name", modelProperty="name")
    @ApiModelProperty(name = "name", required = true, position = 2)
    private String name;

    @JsonProperty(value = "_links", index = 3)
    @ApiModelProperty(name = "_links", readOnly = true, position = 3)
    private CityLinks links = new CityLinks();

    public CityResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    public static CityResponse fromCity(final City city){
        return fromCity(city, null, null);
    }

    public static CityResponse fromCity(
            final City city,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(city).isPresent()) {
            final CityResponse response = new CityResponse();
            response.id = city.getId();
            response.name = city.getName();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CityLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public CityLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
