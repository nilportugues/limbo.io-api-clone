package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class PersonalTraitsDeleted extends DomainEvent<PersonalTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "personal_traits.deleted";

    public PersonalTraitsDeleted(final PersonalTraits personalTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(personalTraits));
    }

    private Payload buildPayload(final PersonalTraits personalTraits) {

        if (!Optional.ofNullable(personalTraits.getId()).isPresent()) {
            return null;
        }

        return new Payload(personalTraits.getId());
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

        public Payload(String personalTraitsId) {
            this.personalTraitsId = personalTraitsId;
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
            return null;
        }
    }
}
