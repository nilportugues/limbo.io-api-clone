package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentJobOfferAdded extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.job_offer.added";

    public TalentJobOfferAdded(final Talent talent, final JobOffer jobOffer) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, jobOffer));
    }

    private Payload buildPayload(final Talent talent, final JobOffer jobOffer) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),jobOffer.getId(),jobOffer.getTitle(),jobOffer.getDescription(),jobOffer.getSalaryMax(),jobOffer.getSalaryMin(),jobOffer.getSalaryCurrency());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "type")
        private final String type = "talent";

        @JsonProperty(value = "id")
        private String talentId;

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String jobOfferId, String talentId, String title, String description, String salaryMax, String salaryMin, String salaryCurrency) {

            this.talentId = talentId;
            this.attributes = new Attributes(jobOfferId, title, description, salaryMax, salaryMin, salaryCurrency);
        }

        @Override
        public String getId() {
            return this.talentId;
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
        @JsonProperty(value = "job_offer_id")
        private String jobOfferId;

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

        public Attributes(String jobOfferId, String title, String description, String salaryMax, String salaryMin, String salaryCurrency) {
            this.jobOfferId = jobOfferId;
            this.title = title;
            this.description = description;
            this.salaryMax = salaryMax;
            this.salaryMin = salaryMin;
            this.salaryCurrency = salaryCurrency;
        }

        @JsonIgnore
        public String getJobOfferId() {
            return this.jobOfferId;
        }

        @JsonIgnore
        public String getTitle() {
            return this.title;
        }
        @JsonIgnore
        public String getDescription() {
            return this.description;
        }
        @JsonIgnore
        public String getSalaryMax() {
            return this.salaryMax;
        }
        @JsonIgnore
        public String getSalaryMin() {
            return this.salaryMin;
        }
        @JsonIgnore
        public String getSalaryCurrency() {
            return this.salaryCurrency;
        }
    }
}
