package io.bandit.limbo.limbo.modules.main.country.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CountryCityAdded extends DomainEvent<Country> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "country.city.added";

    public CountryCityAdded(final Country country, final City city) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(country, city));
    }

    private Payload buildPayload(final Country country, final City city) {

        if (!Optional.ofNullable(country.getId()).isPresent()) {
            return null;
        }

        return new Payload(country.getId(),city.getId(),city.getName());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "type")
        private final String type = "country";

        @JsonProperty(value = "id")
        private String countryId;

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String cityId, String countryId, String name) {

            this.countryId = countryId;
            this.attributes = new Attributes(cityId, name);
        }

        @Override
        public String getId() {
            return this.countryId;
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
        @JsonProperty(value = "city_id")
        private String cityId;

        @JsonProperty(value = "name")
        private String name;

        public Attributes(String cityId, String name) {
            this.cityId = cityId;
            this.name = name;
        }

        @JsonIgnore
        public String getCityId() {
            return this.cityId;
        }

        @JsonIgnore
        public String getName() {
            return this.name;
        }
    }
}
