package io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.application.api.presenters.vnderror.VndError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentProfileForbidden extends VndError {

    public TalentProfileForbidden() {
        final ArrayList<Error> errors = new ArrayList<>();
        errors.add(new Error("Insufficient credentials to access the TalentProfile resource."));

        this.setTotal(1);
        this.setEmbedded(new VndErrors(errors));
    }
}
