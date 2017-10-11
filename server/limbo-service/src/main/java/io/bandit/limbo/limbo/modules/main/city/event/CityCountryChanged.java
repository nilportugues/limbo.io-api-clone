package io.bandit.limbo.limbo.modules.main.city.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CityCountryChanged extends DomainEvent<City> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "city.country.changed";

    public CityCountryChanged(final City city) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(city));
    }

    private Payload buildPayload(final City city) {

        if (!Optional.ofNullable(city.getId()).isPresent()) {
            return null;
        }

        return new Payload(city.getId(),city.getCountry().getId(),city.getCountry().getName(),city.getCountry().getCountryCode());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String cityId;

        @JsonProperty(value = "type")
        private final String type = "city";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String countryId, String cityId, String name, String countryCode) {
            this.cityId = cityId;
            this.attributes = new Attributes(countryId, name, countryCode);
        }

        @Override
        public String getId() {
            return this.cityId;
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
