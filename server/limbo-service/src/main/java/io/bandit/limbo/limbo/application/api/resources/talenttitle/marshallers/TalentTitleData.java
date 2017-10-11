package io.bandit.limbo.limbo.application.api.resources.talenttitle.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentTitleData implements TalentTitleJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "title")
    @ApiModelProperty(name = "title", required = true, position = 1)
    private String title;

    public TalentTitleData() {

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

    public static TalentTitle toTalentTitle(TalentTitleData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return TalentTitle.create(data.getId(),
                data.getTitle());
        }

        return null;
    }
}
