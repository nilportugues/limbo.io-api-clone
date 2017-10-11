package io.bandit.limbo.limbo.modules.main.country.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CountryCountryCodeChanged extends DomainEvent<Country> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "country.country_code.changed";

    public CountryCountryCodeChanged(final Country country) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(country));
    }

    private Payload buildPayload(final Country country) {

        if (!Optional.ofNullable(country.getId()).isPresent()) {
            return null;
        }

        return new Payload(country.getId(),country.getCountryCode());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String countryId;

        @JsonProperty(value = "type")
        private final String type = "country";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String countryId, String countryCode) {
            this.countryId = countryId;
            this.attributes = new Attributes(countryCode);
        }

        @Override
        public String getId() {
            return countryId;
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

        @JsonProperty(value = "country_code")
        private String countryCode;

        public Attributes(String countryCode) {
            this.countryCode = countryCode;
        }

        @JsonIgnore
        public String getCountryCode() {
            return countryCode;
        }
    }
}
