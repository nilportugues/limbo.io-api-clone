package io.bandit.limbo.limbo.modules.main.talentexperience.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentExperienceYearsChanged extends DomainEvent<TalentExperience> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_experience.years.changed";

    public TalentExperienceYearsChanged(final TalentExperience talentExperience) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentExperience));
    }

    private Payload buildPayload(final TalentExperience talentExperience) {

        if (!Optional.ofNullable(talentExperience.getId()).isPresent()) {
            return null;
        }

        return new Payload(talentExperience.getId(),talentExperience.getYears());
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

        public Payload(String talentExperienceId, String years) {
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
        private String years;

        public Attributes(String years) {
            this.years = years;
        }

        @JsonIgnore
        public String getYears() {
            return years;
        }
    }
}
