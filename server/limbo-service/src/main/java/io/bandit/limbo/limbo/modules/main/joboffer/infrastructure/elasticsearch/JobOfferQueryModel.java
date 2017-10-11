package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A JobOffer Read Model.
 */
@Document(indexName = "v1_joboffer", type = "joboffer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobOfferQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "salary_max")
    private String salaryMax;

    @JsonProperty(value = "salary_min")
    private String salaryMin;

    @JsonProperty(value = "salary_currency")
    private String salaryCurrency;

    @JsonProperty(value = "talent")
    private String talentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @JsonIgnore
    public String getTalent() {
        return talentId;
    }

    public void setTalent(String talentId) {
        this.talentId = talentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobOfferQueryModel jobOffer = (JobOfferQueryModel) o;

        return !(jobOffer.id == null || id == null) && Objects.equals(id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobOfferQueryModel{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", salaryMax='" + salaryMax + "'" +
            ", salaryMin='" + salaryMin + "'" +
            ", salaryCurrency='" + salaryCurrency + "'" +
            '}';
    }
}
