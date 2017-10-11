package io.bandit.limbo.limbo.application.api.resources.joboffer.marshallers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterModel;
import io.bandit.limbo.limbo.modules.shared.annotation.FilterProperty;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

@ApiModel
@FilterModel(model = JobOffer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobOfferResponse implements JobOfferJsonResponse {

    @JsonProperty(value = "id")
    @FilterProperty(filterName = "id", modelProperty="id")
    @ApiModelProperty(name = "id", required = true, position = 1)
    private String id;

    @JsonProperty(value = "title", index = 2)
    @FilterProperty(filterName = "title", modelProperty="title")
    @ApiModelProperty(name = "title", required = true, position = 2)
    private String title;

    @JsonProperty(value = "description", index = 3)
    @FilterProperty(filterName = "description", modelProperty="description")
    @ApiModelProperty(name = "description", required = true, position = 3)
    private String description;

    @JsonProperty(value = "salary_max", index = 4)
    @FilterProperty(filterName = "salary_max", modelProperty="salaryMax")
    @ApiModelProperty(name = "salary_max", required = true, position = 4)
    private String salaryMax;

    @JsonProperty(value = "salary_min", index = 5)
    @FilterProperty(filterName = "salary_min", modelProperty="salaryMin")
    @ApiModelProperty(name = "salary_min", required = true, position = 5)
    private String salaryMin;

    @JsonProperty(value = "salary_currency", index = 6)
    @FilterProperty(filterName = "salary_currency", modelProperty="salaryCurrency")
    @ApiModelProperty(name = "salary_currency", required = true, position = 6)
    private String salaryCurrency;

    @JsonProperty(value = "_links", index = 7)
    @ApiModelProperty(name = "_links", readOnly = true, position = 7)
    private JobOfferLinks links = new JobOfferLinks();

    public JobOfferResponse() {

    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    @JsonIgnore
    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public String getSalaryMax() {
        return salaryMax;
    }

    @JsonIgnore
    public String getSalaryMin() {
        return salaryMin;
    }

    @JsonIgnore
    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public static JobOfferResponse fromJobOffer(final JobOffer jobOffer){
        return fromJobOffer(jobOffer, null, null);
    }

    public static JobOfferResponse fromJobOffer(
            final JobOffer jobOffer,
            final String pageUrl,
            final String selfUrl) {

        if (Optional.ofNullable(jobOffer).isPresent()) {
            final JobOfferResponse response = new JobOfferResponse();
            response.id = jobOffer.getId();
            response.title = jobOffer.getTitle();
            response.description = jobOffer.getDescription();
            response.salaryMax = jobOffer.getSalaryMax();
            response.salaryMin = jobOffer.getSalaryMin();
            response.salaryCurrency = jobOffer.getSalaryCurrency();

            response.links.setSelf(selfUrl);

            return response;
        }

        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class JobOfferLinks {

        @JsonProperty(value = "self", index = 0)
        @ApiModelProperty(name = "self", readOnly = true, position = 0)
        private Link self;

        public JobOfferLinks() {
        }

        public void setSelf(final String self) {
            this.self = new Link(self);
        }
    }
}
