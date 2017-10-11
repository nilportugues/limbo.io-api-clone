package io.bandit.limbo.limbo.modules.main.joboffer.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class JobOfferDeleted extends DomainEvent<JobOffer> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "job_offer.deleted";

    public JobOfferDeleted(final JobOffer jobOffer) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(jobOffer));
    }

    private Payload buildPayload(final JobOffer jobOffer) {

        if (!Optional.ofNullable(jobOffer.getId()).isPresent()) {
            return null;
        }

        return new Payload(jobOffer.getId());
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

        public Payload(String jobOfferId) {
            this.jobOfferId = jobOfferId;
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
            return null;
        }
    }
}
