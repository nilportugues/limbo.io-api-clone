package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobOffer Model.
 */
@Entity
@Table(name = "job_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobOfferJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "salary_max")
    private String salaryMax;

    @Column(name = "salary_min")
    private String salaryMin;

    @Column(name = "salary_currency")
    private String salaryCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    private TalentJpaModel talent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalaryMax() {
        return salaryMax;
    }


    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getSalaryMin() {
        return salaryMin;
    }


    public void setSalaryMin(String salaryMin) {
        this.salaryMin = salaryMin;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }


    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public TalentJpaModel getTalent() {
        return talent;
    }

    public void setTalent(TalentJpaModel talent) {
        this.talent = talent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobOfferJpaModel jobOffer = (JobOfferJpaModel) o;

        return !(jobOffer.id == null || id == null) && Objects.equals(id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobOfferJpaModel{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", salaryMax='" + salaryMax + "'" +
            ", salaryMin='" + salaryMin + "'" +
            ", salaryCurrency='" + salaryCurrency + "'" +
            '}';
    }
}
