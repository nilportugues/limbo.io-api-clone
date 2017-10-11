package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PersonalTraitsDescriptionChanged extends DomainEvent<PersonalTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "personal_traits.description.changed";

    public PersonalTraitsDescriptionChanged(final PersonalTraits personalTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(personalTraits));
    }

    private Payload buildPayload(final PersonalTraits personalTraits) {

        if (!Optional.ofNullable(personalTraits.getId()).isPresent()) {
            return null;
        }

        return new Payload(personalTraits.getId(),personalTraits.getDescription());
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

        public Payload(String personalTraitsId, String description) {
            this.personalTraitsId = personalTraitsId;
            this.attributes = new Attributes(description);
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
        private String description;

        public Attributes(String description) {
            this.description = description;
        }

        @JsonIgnore
        public String getDescription() {
            return description;
        }
    }
}
