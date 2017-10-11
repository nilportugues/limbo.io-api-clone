package io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.application.api.presenters.vnderror.VndError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;


@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TalentProfileInternalServerError extends VndError {

    public TalentProfileInternalServerError() {
        final ArrayList<Error> errors = new ArrayList<>();
        errors.add(new Error("TalentProfile resource is not reachable."));

        this.setTotal(1);
        this.setEmbedded(new VndErrors(errors));
    }
}
