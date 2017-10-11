package io.bandit.limbo.limbo.application.api.resources.country.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryData implements CountryJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "name")
    @ApiModelProperty(name = "name", required = true, position = 1)
    private String name;
    @JsonProperty(value = "country_code")
    @ApiModelProperty(name = "country_code", required = true, position = 2)
    private String countryCode;

    public CountryData() {

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
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public static Country toCountry(CountryData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return Country.create(data.getId(),
                data.getName(),
                data.getCountryCode());
        }

        return null;
    }
}
