package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentCountryChanged extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.country.changed";

    public TalentCountryChanged(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),talent.getCountry().getId(),talent.getCountry().getName(),talent.getCountry().getCountryCode());
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

        public Payload(String countryId, String talentId, String name, String countryCode) {
            this.talentId = talentId;
            this.attributes = new Attributes(countryId, name, countryCode);
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
        @JsonProperty(value = "country_id")
        private String countryId;


        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "country_code")
        private String countryCode;

        public Attributes(String countryId, String name, String countryCode) {

            this.countryId = countryId;
            this.name = name;
            this.countryCode = countryCode;
        }

        @JsonIgnore
        public String getCountryId() {
            return this.countryId;
        }

        @JsonIgnore
        public String getName() {
            return this.name;
        }
        @JsonIgnore
        public String getCountryCode() {
            return this.countryCode;
        }
    }
}
