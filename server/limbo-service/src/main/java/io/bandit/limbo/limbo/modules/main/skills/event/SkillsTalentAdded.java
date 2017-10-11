package io.bandit.limbo.limbo.modules.main.skills.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class SkillsTalentAdded extends DomainEvent<Skills> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "skills.talent.added";

    public SkillsTalentAdded(final Skills skills, final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(skills, talent));
    }

    private Payload buildPayload(final Skills skills, final Talent talent) {

        if (!Optional.ofNullable(skills.getId()).isPresent()) {
            return null;
        }
        return new Payload(skills.getId(), talent.getId());
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

        public Payload(String skillsId, String talentId) {
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
