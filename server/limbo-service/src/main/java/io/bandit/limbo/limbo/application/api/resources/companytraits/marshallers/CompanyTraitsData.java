package io.bandit.limbo.limbo.application.api.resources.companytraits.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyTraitsData implements CompanyTraitsJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "title")
    @ApiModelProperty(name = "title", required = true, position = 1)
    private String title;

    public CompanyTraitsData() {

    }

    @JsonIgnore
    public String getId() {
        return (!Optional.ofNullable(id).isPresent()) ? null : id;
    }


    @JsonIgnore
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static CompanyTraits toCompanyTraits(CompanyTraitsData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return CompanyTraits.create(data.getId(),
                data.getTitle());
        }

        return null;
    }
}
