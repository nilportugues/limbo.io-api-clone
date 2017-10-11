package io.bandit.limbo.limbo.modules.main.city.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class CityCountryAdded extends DomainEvent<City> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "city.country.added";

    public CityCountryAdded(final City city, final Country country) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(city, country));
    }

    private Payload buildPayload(final City city, final Country country) {

        if (!Optional.ofNullable(city.getId()).isPresent()) {
            return null;
        }
        return new Payload(city.getId(), country.getId());
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

        public Payload(String cityId, String countryId) {
            this.cityId = cityId;
            this.attributes = new Attributes(countryId);
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

        public Attributes(String countryId) {
            this.countryId = countryId;
        }

        @JsonIgnore
        public String getCountryId() {
            return this.countryId;
        }
    }
}
