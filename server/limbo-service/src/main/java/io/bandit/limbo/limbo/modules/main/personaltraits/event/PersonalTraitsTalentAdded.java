package io.bandit.limbo.limbo.modules.main.personaltraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class PersonalTraitsTalentAdded extends DomainEvent<PersonalTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "personal_traits.talent.added";

    public PersonalTraitsTalentAdded(final PersonalTraits personalTraits, final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(personalTraits, talent));
    }

    private Payload buildPayload(final PersonalTraits personalTraits, final Talent talent) {

        if (!Optional.ofNullable(personalTraits.getId()).isPresent()) {
            return null;
        }
        return new Payload(personalTraits.getId(), talent.getId());
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

        public Payload(String personalTraitsId, String talentId) {
            this.personalTraitsId = personalTraitsId;
            this.attributes = new Attributes(talentId);
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

        public Attributes(String talentId) {
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }
    }
}
