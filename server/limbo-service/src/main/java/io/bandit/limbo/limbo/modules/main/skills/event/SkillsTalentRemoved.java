package io.bandit.limbo.limbo.modules.main.skills.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
        import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SkillsTalentRemoved extends DomainEvent<Skills> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "skills.talent.removed";

    public SkillsTalentRemoved(final Skills skills) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(skills));
    }

    private Payload buildPayload(final Skills skills) {

        if (!Optional.ofNullable(skills.getId()).isPresent()) {
            return null;
        }

        return new Payload(skills.getId(),skills.getTalent().getId());
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

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String talentId, String skillsId) {
            this.skillsId = skillsId;
            this.attributes = new Attributes(talentId);
        }

        @Override
        public String getId() {
            return this.skillsId;
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

        public Attributes(String talentId) {
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }
    }
}
