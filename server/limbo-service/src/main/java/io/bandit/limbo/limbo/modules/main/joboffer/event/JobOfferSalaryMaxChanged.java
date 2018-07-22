package io.bandit.limbo.limbo.modules.main.joboffer.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class JobOfferSalaryMaxChanged extends DomainEvent<JobOffer> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "job_offer.salary_max.changed";

    public JobOfferSalaryMaxChanged(final JobOffer jobOffer) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(jobOffer));
    }

    private Payload buildPayload(final JobOffer jobOffer) {

        if (!Optional.ofNullable(jobOffer.getId()).isPresent()) {
            return null;
        }

        return new Payload(jobOffer.getId(),jobOffer.getSalaryMax());
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

        public Payload(String jobOfferId, String salaryMax) {
            this.jobOfferId = jobOfferId;
            this.attributes = new Attributes(salaryMax);
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

        @JsonProperty(value = "salary_max")
        private String salaryMax;

        public Attributes(String salaryMax) {
            this.salaryMax = salaryMax;
        }

        @JsonIgnore
        public String getSalaryMax() {
            return salaryMax;
        }
    }
}