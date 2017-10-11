package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentTalentProfileChanged extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.talent_profile.changed";

    public TalentTalentProfileChanged(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),talent.getTalentProfile().getId(),talent.getTalentProfile().getFirstName(),talent.getTalentProfile().getLastName());
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

        public Payload(String talentProfileId, String talentId, String firstName, String lastName) {
            this.talentId = talentId;
            this.attributes = new Attributes(talentProfileId, firstName, lastName);
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
        @JsonProperty(value = "talent_profile_id")
        private String talentProfileId;


        @JsonProperty(value = "first_name")
        private String firstName;

        @JsonProperty(value = "last_name")
        private String lastName;

        public Attributes(String talentProfileId, String firstName, String lastName) {

            this.talentProfileId = talentProfileId;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @JsonIgnore
        public String getTalentProfileId() {
            return this.talentProfileId;
        }

        @JsonIgnore
        public String getFirstName() {
            return this.firstName;
        }
        @JsonIgnore
        public String getLastName() {
            return this.lastName;
        }
    }
}
