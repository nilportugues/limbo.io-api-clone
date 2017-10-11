package io.bandit.limbo.limbo.application.api.resources.joboffer.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Optional;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobOfferData implements JobOfferJsonResponse {

    @JsonProperty(value = "id")
    @ApiModelProperty(name = "id", required = false, position = 0)
    private String id;

    @JsonProperty(value = "title")
    @ApiModelProperty(name = "title", required = true, position = 1)
    private String title;
    @JsonProperty(value = "description")
    @ApiModelProperty(name = "description", required = true, position = 2)
    private String description;
    @JsonProperty(value = "salary_max")
    @ApiModelProperty(name = "salary_max", required = true, position = 3)
    private String salaryMax;
    @JsonProperty(value = "salary_min")
    @ApiModelProperty(name = "salary_min", required = true, position = 4)
    private String salaryMin;
    @JsonProperty(value = "salary_currency")
    @ApiModelProperty(name = "salary_currency", required = true, position = 5)
    private String salaryCurrency;

    public JobOfferData() {

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

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public String getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    @JsonIgnore
    public String getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(String salaryMin) {
        this.salaryMin = salaryMin;
    }

    @JsonIgnore
    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public static JobOffer toJobOffer(JobOfferData data) {
        if (Optional.ofNullable(data).isPresent()) {
            return JobOffer.create(data.getId(),
                data.getTitle(),
                data.getDescription(),
                data.getSalaryMax(),
                data.getSalaryMin(),
                data.getSalaryCurrency());
        }

        return null;
    }
}
