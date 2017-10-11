package io.bandit.limbo.limbo.modules.main.talentexperience.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class TalentExperienceUpdated extends DomainEvent<TalentExperience> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_experience.updated";

    public TalentExperienceUpdated(final TalentExperience talentExperience) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentExperience));
    }

    private Payload buildPayload(final TalentExperience talentExperience) {

        if (!Optional.ofNullable(talentExperience.getId()).isPresent()) {
            return null;
        }


        return new Payload(talentExperience.getId(), talentExperience.getYears());
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

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(final String talentExperienceId, final String years) {
            this.talentExperienceId = talentExperienceId;
            this.attributes = new Attributes(years);
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
            return attributes;
        }
    }

    public class Attributes implements DomainEvent.Attributes {

        @JsonProperty(value = "years")
        private final String years;

        public Attributes(final String years) {

            this.years = years;
        }

        @JsonIgnore
        public String getYears() {
            return years;
        }
    }
}
