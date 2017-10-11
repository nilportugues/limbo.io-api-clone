package io.bandit.limbo.limbo.modules.main.skills.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class SkillsUpdated extends DomainEvent<Skills> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "skills.updated";

    public SkillsUpdated(final Skills skills) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(skills));
    }

    private Payload buildPayload(final Skills skills) {

        if (!Optional.ofNullable(skills.getId()).isPresent()) {
            return null;
        }

        String talentId = null;
        if (Optional.ofNullable(skills.getTalent()).isPresent()) {
            talentId = skills.getTalent().getId();
        }


        return new Payload(skills.getId(), skills.getSkill(), talentId);
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

        public Payload(final String skillsId, final String skill, final String talentId) {
            this.skillsId = skillsId;
            this.attributes = new Attributes(skill, talentId);
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
            return attributes;
        }
    }

    public class Attributes implements DomainEvent.Attributes {

        @JsonProperty(value = "skill")
        private final String skill;

        @JsonProperty(value = "talent")
        private final String talentId;

        public Attributes(final String skill, final String talentId) {

            this.skill = skill;
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getSkill() {
            return skill;
        }

        @JsonIgnore
        public String getTalent() {
            return talentId;
        }
    }
}
