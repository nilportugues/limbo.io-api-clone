package io.bandit.limbo.limbo.modules.main.joboffer.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class JobOfferTalentChanged extends DomainEvent<JobOffer> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "job_offer.talent.changed";

    public JobOfferTalentChanged(final JobOffer jobOffer) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(jobOffer));
    }

    private Payload buildPayload(final JobOffer jobOffer) {

        if (!Optional.ofNullable(jobOffer.getId()).isPresent()) {
            return null;
        }

        return new Payload(jobOffer.getId(),jobOffer.getTalent().getId(),jobOffer.getTalent().getEmail(),jobOffer.getTalent().getPassword());
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

        public Payload(String talentId, String jobOfferId, String email, String password) {
            this.jobOfferId = jobOfferId;
            this.attributes = new Attributes(talentId, email, password);
        }

        @Override
        public String getId() {
            return this.jobOfferId;
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
        @JsonProperty(value = "talent_id")
        private String talentId;


        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "password")
        private String password;

        public Attributes(String talentId, String email, String password) {

            this.talentId = talentId;
            this.email = email;
            this.password = password;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }

        @JsonIgnore
        public String getEmail() {
            return this.email;
        }
        @JsonIgnore
        public String getPassword() {
            return this.password;
        }
    }
}
