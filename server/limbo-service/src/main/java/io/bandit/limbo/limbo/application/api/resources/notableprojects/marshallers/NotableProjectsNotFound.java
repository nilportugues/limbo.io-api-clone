package io.bandit.limbo.limbo.application.api.resources.notableprojects.marshallers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.application.api.presenters.vnderror.VndError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotableProjectsNotFound extends VndError {

    public NotableProjectsNotFound(String message) {
        final ArrayList<Error> errors = new ArrayList<>();
        errors.add(new Error("NotableProjects Not Found"));

        this.setTotal(1);
        this.setEmbedded(new VndErrors(errors));
    }
}
