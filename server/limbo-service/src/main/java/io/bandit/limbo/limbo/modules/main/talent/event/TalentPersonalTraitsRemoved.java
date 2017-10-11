package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class TalentPersonalTraitsRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.personal_traits.removed";

    public TalentPersonalTraitsRemoved(final Talent talent, final PersonalTraits personalTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, personalTraits));
    }

    private Payload buildPayload(final Talent talent, final PersonalTraits personalTraits) {

        final String talentId = talent.getId();
        final String personalTraitsId = personalTraits.getId();
        if (!Optional.ofNullable(talentId).isPresent() || !Optional.ofNullable(personalTraitsId).isPresent()) {
            return null;
        }

        return new Payload(personalTraitsId, talentId);
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

        public Payload(String personalTraitsId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(personalTraitsId);
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
        @JsonProperty(value = "personal_traits_id")
        private String personalTraitsId;

        public Attributes(String personalTraitsId) {
            this.personalTraitsId = personalTraitsId;
        }

        @JsonIgnore
        public String getPersonalTraitsId() {
            return this.personalTraitsId;
        }
    }
}
