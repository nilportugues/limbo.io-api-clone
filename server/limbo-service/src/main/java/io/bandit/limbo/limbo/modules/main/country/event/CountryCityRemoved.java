package io.bandit.limbo.limbo.modules.main.country.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class CountryCityRemoved extends DomainEvent<Country> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "country.city.removed";

    public CountryCityRemoved(final Country country, final City city) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(country, city));
    }

    private Payload buildPayload(final Country country, final City city) {

        final String countryId = country.getId();
        final String cityId = city.getId();
        if (!Optional.ofNullable(countryId).isPresent() || !Optional.ofNullable(cityId).isPresent()) {
            return null;
        }

        return new Payload(cityId, countryId);
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

        public Payload(String cityId, String countryId) {
            this.countryId = countryId;
            this.attributes = new Attributes(cityId);
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

        public Attributes(String cityId) {
            this.cityId = cityId;
        }

        @JsonIgnore
        public String getCityId() {
            return this.cityId;
        }
    }
}
