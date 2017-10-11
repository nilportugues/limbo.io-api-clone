package io.bandit.limbo.limbo.modules.main.joboffer.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class JobOfferCreated extends DomainEvent<JobOffer> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "job_offer.created";

    public JobOfferCreated(final JobOffer jobOffer) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(jobOffer));
    }

    private Payload buildPayload(final JobOffer jobOffer) {

        if (!Optional.ofNullable(jobOffer.getId()).isPresent()) {
            return null;
        }

        String talentId = null;
        if (Optional.ofNullable(jobOffer.getTalent()).isPresent()) {
            talentId = jobOffer.getTalent().getId();
        }


        return new Payload(jobOffer.getId(), jobOffer.getTitle(), jobOffer.getDescription(), jobOffer.getSalaryMax(), jobOffer.getSalaryMin(), jobOffer.getSalaryCurrency(), talentId);
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String jobOfferId;

        @JsonProperty(value = "type")
        private final String type = "job_offer";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(final String jobOfferId, final String title, final String description, final String salaryMax, final String salaryMin, final String salaryCurrency, final String talentId) {
            this.jobOfferId = jobOfferId;
            this.attributes = new Attributes(title, description, salaryMax, salaryMin, salaryCurrency, talentId);
        }

        @Override
        public String getId() {
            return jobOfferId;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public Attributes getAttributes() {
            return attributes;
        }
    }

    public class Attributes implements DomainEvent.Attributes {

        @JsonProperty(value = "title")
        private final String title;

        @JsonProperty(value = "description")
        private final String description;

        @JsonProperty(value = "salary_max")
        private final String salaryMax;

        @JsonProperty(value = "salary_min")
        private final String salaryMin;

        @JsonProperty(value = "salary_currency")
        private final String salaryCurrency;

        @JsonProperty(value = "talent")
        private final String talentId;

        public Attributes(final String title, final String description, final String salaryMax, final String salaryMin, final String salaryCurrency, final String talentId) {

            this.title = title;
            this.description = description;
            this.salaryMax = salaryMax;
            this.salaryMin = salaryMin;
            this.salaryCurrency = salaryCurrency;
            this.talentId = talentId;
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

        @JsonIgnore
        public String getTalent() {
            return talentId;
        }
    }
}
