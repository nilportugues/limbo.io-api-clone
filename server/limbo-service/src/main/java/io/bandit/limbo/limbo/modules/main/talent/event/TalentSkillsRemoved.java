package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class TalentSkillsRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.skills.removed";

    public TalentSkillsRemoved(final Talent talent, final Skills skills) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, skills));
    }

    private Payload buildPayload(final Talent talent, final Skills skills) {

        final String talentId = talent.getId();
        final String skillsId = skills.getId();
        if (!Optional.ofNullable(talentId).isPresent() || !Optional.ofNullable(skillsId).isPresent()) {
            return null;
        }

        return new Payload(skillsId, talentId);
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

        public Payload(String skillsId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(skillsId);
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
        @JsonProperty(value = "skills_id")
        private String skillsId;

        public Attributes(String skillsId) {
            this.skillsId = skillsId;
        }

        @JsonIgnore
        public String getSkillsId() {
            return this.skillsId;
        }
    }
}
