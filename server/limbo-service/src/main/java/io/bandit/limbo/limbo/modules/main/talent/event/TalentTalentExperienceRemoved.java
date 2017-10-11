package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
        import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentTalentExperienceRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.talent_experience.removed";

    public TalentTalentExperienceRemoved(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),talent.getTalentExperience().getId());
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

        public Payload(String talentExperienceId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(talentExperienceId);
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
        @JsonProperty(value = "talent_experience_id")
        private String talentExperienceId;

        public Attributes(String talentExperienceId) {
            this.talentExperienceId = talentExperienceId;
        }

        @JsonIgnore
        public String getTalentExperienceId() {
            return this.talentExperienceId;
        }
    }
}
