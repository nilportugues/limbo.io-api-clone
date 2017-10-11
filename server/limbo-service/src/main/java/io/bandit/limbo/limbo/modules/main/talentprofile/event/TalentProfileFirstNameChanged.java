package io.bandit.limbo.limbo.modules.main.talentprofile.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentProfileFirstNameChanged extends DomainEvent<TalentProfile> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_profile.first_name.changed";

    public TalentProfileFirstNameChanged(final TalentProfile talentProfile) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentProfile));
    }

    private Payload buildPayload(final TalentProfile talentProfile) {

        if (!Optional.ofNullable(talentProfile.getId()).isPresent()) {
            return null;
        }

        return new Payload(talentProfile.getId(),talentProfile.getFirstName());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String talentProfileId;

        @JsonProperty(value = "type")
        private final String type = "talent_profile";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String talentProfileId, String firstName) {
            this.talentProfileId = talentProfileId;
            this.attributes = new Attributes(firstName);
        }

        @Override
        public String getId() {
            return talentProfileId;
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

        @JsonProperty(value = "first_name")
        private String firstName;

        public Attributes(String firstName) {
            this.firstName = firstName;
        }

        @JsonIgnore
        public String getFirstName() {
            return firstName;
        }
    }
}
