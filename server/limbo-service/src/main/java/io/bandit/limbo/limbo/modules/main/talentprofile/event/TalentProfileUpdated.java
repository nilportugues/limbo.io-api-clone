package io.bandit.limbo.limbo.modules.main.talentprofile.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class TalentProfileUpdated extends DomainEvent<TalentProfile> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_profile.updated";

    public TalentProfileUpdated(final TalentProfile talentProfile) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentProfile));
    }

    private Payload buildPayload(final TalentProfile talentProfile) {

        if (!Optional.ofNullable(talentProfile.getId()).isPresent()) {
            return null;
        }


        return new Payload(talentProfile.getId(), talentProfile.getFirstName(), talentProfile.getLastName());
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

        public Payload(final String talentProfileId, final String firstName, final String lastName) {
            this.talentProfileId = talentProfileId;
            this.attributes = new Attributes(firstName, lastName);
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
        private final String firstName;

        @JsonProperty(value = "last_name")
        private final String lastName;

        public Attributes(final String firstName, final String lastName) {

            this.firstName = firstName;
            this.lastName = lastName;
        }

        @JsonIgnore
        public String getFirstName() {
            return firstName;
        }
        @JsonIgnore
        public String getLastName() {
            return lastName;
        }
    }
}
