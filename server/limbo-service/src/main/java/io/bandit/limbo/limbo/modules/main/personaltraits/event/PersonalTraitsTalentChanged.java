package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PersonalTraitsTalentChanged extends DomainEvent<PersonalTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "personal_traits.talent.changed";

    public PersonalTraitsTalentChanged(final PersonalTraits personalTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(personalTraits));
    }

    private Payload buildPayload(final PersonalTraits personalTraits) {

        if (!Optional.ofNullable(personalTraits.getId()).isPresent()) {
            return null;
        }

        return new Payload(personalTraits.getId(),personalTraits.getTalent().getId(),personalTraits.getTalent().getEmail(),personalTraits.getTalent().getPassword());
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

        public Payload(String talentId, String personalTraitsId, String email, String password) {
            this.personalTraitsId = personalTraitsId;
            this.attributes = new Attributes(talentId, email, password);
        }

        @Override
        public String getId() {
            return this.personalTraitsId;
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


        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "password")
        private String password;

        public Attributes(String talentId, String email, String password) {

            this.talentId = talentId;
            this.email = email;
            this.password = password;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }

        @JsonIgnore
        public String getEmail() {
            return this.email;
        }
        @JsonIgnore
        public String getPassword() {
            return this.password;
        }
    }
}
