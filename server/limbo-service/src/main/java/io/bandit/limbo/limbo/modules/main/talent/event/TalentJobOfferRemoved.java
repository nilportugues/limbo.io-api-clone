package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class TalentJobOfferRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.job_offer.removed";

    public TalentJobOfferRemoved(final Talent talent, final JobOffer jobOffer) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, jobOffer));
    }

    private Payload buildPayload(final Talent talent, final JobOffer jobOffer) {

        final String talentId = talent.getId();
        final String jobOfferId = jobOffer.getId();
        if (!Optional.ofNullable(talentId).isPresent() || !Optional.ofNullable(jobOfferId).isPresent()) {
            return null;
        }

        return new Payload(jobOfferId, talentId);
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {
        @JsonProperty(value = "id")
        private String talentId;

        @JsonProperty(value = "type")
        private final String type = "talent";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String jobOfferId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(jobOfferId);
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

        public Attributes(String jobOfferId) {
            this.jobOfferId = jobOfferId;
        }

        @JsonIgnore
        public String getJobOfferId() {
            return this.jobOfferId;
        }
    }
}
