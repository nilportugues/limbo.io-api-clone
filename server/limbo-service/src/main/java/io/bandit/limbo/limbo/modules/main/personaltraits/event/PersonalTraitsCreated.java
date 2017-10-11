package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class PersonalTraitsCreated extends DomainEvent<PersonalTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "personal_traits.created";

    public PersonalTraitsCreated(final PersonalTraits personalTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(personalTraits));
    }

    private Payload buildPayload(final PersonalTraits personalTraits) {

        if (!Optional.ofNullable(personalTraits.getId()).isPresent()) {
            return null;
        }

        String talentId = null;
        if (Optional.ofNullable(personalTraits.getTalent()).isPresent()) {
            talentId = personalTraits.getTalent().getId();
        }


        return new Payload(personalTraits.getId(), personalTraits.getDescription(), talentId);
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String personalTraitsId;

        @JsonProperty(value = "type")
        private final String type = "personal_traits";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(final String personalTraitsId, final String description, final String talentId) {
            this.personalTraitsId = personalTraitsId;
            this.attributes = new Attributes(description, talentId);
        }

        @Override
        public String getId() {
            return personalTraitsId;
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

        @JsonProperty(value = "description")
        private final String description;

        @JsonProperty(value = "talent")
        private final String talentId;

        public Attributes(final String description, final String talentId) {

            this.description = description;
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getDescription() {
            return description;
        }

        @JsonIgnore
        public String getTalent() {
            return talentId;
        }
    }
}
