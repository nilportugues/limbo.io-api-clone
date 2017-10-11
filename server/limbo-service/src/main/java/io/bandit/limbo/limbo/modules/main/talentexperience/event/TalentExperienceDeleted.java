package io.bandit.limbo.limbo.modules.main.talentexperience.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class TalentExperienceDeleted extends DomainEvent<TalentExperience> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_experience.deleted";

    public TalentExperienceDeleted(final TalentExperience talentExperience) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentExperience));
    }

    private Payload buildPayload(final TalentExperience talentExperience) {

        if (!Optional.ofNullable(talentExperience.getId()).isPresent()) {
            return null;
        }

        return new Payload(talentExperience.getId());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String talentExperienceId;

        @JsonProperty(value = "type")
        private final String type = "talent_experience";

        public Payload(String talentExperienceId) {
            this.talentExperienceId = talentExperienceId;
        }

        @Override
        public String getId() {
            return talentExperienceId;
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
