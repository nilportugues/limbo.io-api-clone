package io.bandit.limbo.limbo.modules.main.skills.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class SkillsDeleted extends DomainEvent<Skills> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "skills.deleted";

    public SkillsDeleted(final Skills skills) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(skills));
    }

    private Payload buildPayload(final Skills skills) {

        if (!Optional.ofNullable(skills.getId()).isPresent()) {
            return null;
        }

        return new Payload(skills.getId());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String skillsId;

        @JsonProperty(value = "type")
        private final String type = "skills";

        public Payload(String skillsId) {
            this.skillsId = skillsId;
        }

        @Override
        public String getId() {
            return skillsId;
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
