package io.bandit.limbo.limbo.modules.main.talentprofile.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class TalentProfileDeleted extends DomainEvent<TalentProfile> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_profile.deleted";

    public TalentProfileDeleted(final TalentProfile talentProfile) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentProfile));
    }

    private Payload buildPayload(final TalentProfile talentProfile) {

        if (!Optional.ofNullable(talentProfile.getId()).isPresent()) {
            return null;
        }

        return new Payload(talentProfile.getId());
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

        public Payload(String talentProfileId) {
            this.talentProfileId = talentProfileId;
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
            return null;
        }
    }
}
