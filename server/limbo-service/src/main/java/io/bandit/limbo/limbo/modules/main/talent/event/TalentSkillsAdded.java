package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentSkillsAdded extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.skills.added";

    public TalentSkillsAdded(final Talent talent, final Skills skills) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, skills));
    }

    private Payload buildPayload(final Talent talent, final Skills skills) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),skills.getId(),skills.getSkill());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "type")
        private final String type = "talent";

        @JsonProperty(value = "id")
        private String talentId;

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String skillsId, String talentId, String skill) {

            this.talentId = talentId;
            this.attributes = new Attributes(skillsId, skill);
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

        @JsonProperty(value = "skill")
        private String skill;

        public Attributes(String skillsId, String skill) {
            this.skillsId = skillsId;
            this.skill = skill;
        }

        @JsonIgnore
        public String getSkillsId() {
            return this.skillsId;
        }

        @JsonIgnore
        public String getSkill() {
            return this.skill;
        }
    }
}
