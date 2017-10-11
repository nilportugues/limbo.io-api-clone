package io.bandit.limbo.limbo.modules.main.joboffer.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.joboffer.event.*;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferTitleChanged;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferDescriptionChanged;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferSalaryMaxChanged;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferSalaryMinChanged;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferSalaryCurrencyChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A JobOffer Domain Entity.
 */
public class JobOffer extends Aggregate {

    private String id;

    private String title;
    private String description;
    private String salaryMax;
    private String salaryMin;
    private String salaryCurrency;
    private Talent talent;

    public static JobOffer create(String id, String title, String description, String salaryMax, String salaryMin, String salaryCurrency) {
        final JobOffer self = new JobOffer();
        self.id = id;
        self.title = title;
        self.description = description;
        self.salaryMax = salaryMax;
        self.salaryMin = salaryMin;
        self.salaryCurrency = salaryCurrency;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new JobOfferImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if (null != title && !title.equals(this.title)) {
            this.title = title;
            apply(new JobOfferTitleChanged(this));
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (null != description && !description.equals(this.description)) {
            this.description = description;
            apply(new JobOfferDescriptionChanged(this));
        }
    }


    public String getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(String salaryMax) {

        if (null != salaryMax && !salaryMax.equals(this.salaryMax)) {
            this.salaryMax = salaryMax;
            apply(new JobOfferSalaryMaxChanged(this));
        }
    }


    public String getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(String salaryMin) {

        if (null != salaryMin && !salaryMin.equals(this.salaryMin)) {
            this.salaryMin = salaryMin;
            apply(new JobOfferSalaryMinChanged(this));
        }
    }


    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {

        if (null != salaryCurrency && !salaryCurrency.equals(this.salaryCurrency)) {
            this.salaryCurrency = salaryCurrency;
            apply(new JobOfferSalaryCurrencyChanged(this));
        }
    }
    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        if (!Objects.equals(this.talent, talent)) {
            removeTalent();
            this.talent = talent;
            apply(new JobOfferTalentChanged(this));
        }
    }

    public void removeTalent() {
        if (null != this.talent) {
            apply(new JobOfferTalentChanged(this));
            this.talent = null;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobOffer jobOffer = (JobOffer) o;

        return !(jobOffer.id == null || id == null) && Objects.equals(id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobOffer{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", salaryMax='" + salaryMax + "'" +
            ", salaryMin='" + salaryMin + "'" +
            ", salaryCurrency='" + salaryCurrency + "'" +
            '}';
    }
}
