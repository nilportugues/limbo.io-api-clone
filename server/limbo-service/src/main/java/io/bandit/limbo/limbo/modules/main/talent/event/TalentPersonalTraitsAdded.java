package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentPersonalTraitsAdded extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.personal_traits.added";

    public TalentPersonalTraitsAdded(final Talent talent, final PersonalTraits personalTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, personalTraits));
    }

    private Payload buildPayload(final Talent talent, final PersonalTraits personalTraits) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),personalTraits.getId(),personalTraits.getDescription());
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

        public Payload(String personalTraitsId, String talentId, String description) {

            this.talentId = talentId;
            this.attributes = new Attributes(personalTraitsId, description);
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

        @JsonProperty(value = "description")
        private String description;

        public Attributes(String personalTraitsId, String description) {
            this.personalTraitsId = personalTraitsId;
            this.description = description;
        }

        @JsonIgnore
        public String getPersonalTraitsId() {
            return this.personalTraitsId;
        }

        @JsonIgnore
        public String getDescription() {
            return this.description;
        }
    }
}
