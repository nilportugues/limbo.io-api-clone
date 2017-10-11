package io.bandit.limbo.limbo.modules.main.city.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class CityUpdated extends DomainEvent<City> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "city.updated";

    public CityUpdated(final City city) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(city));
    }

    private Payload buildPayload(final City city) {

        if (!Optional.ofNullable(city.getId()).isPresent()) {
            return null;
        }

        String countryId = null;
        if (Optional.ofNullable(city.getCountry()).isPresent()) {
            countryId = city.getCountry().getId();
        }


        return new Payload(city.getId(), city.getName(), countryId);
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

        public Payload(final String cityId, final String name, final String countryId) {
            this.cityId = cityId;
            this.attributes = new Attributes(name, countryId);
        }

        @Override
        public String getId() {
            return cityId;
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

        @JsonProperty(value = "name")
        private final String name;

        @JsonProperty(value = "country")
        private final String countryId;

        public Attributes(final String name, final String countryId) {

            this.name = name;
            this.countryId = countryId;
        }

        @JsonIgnore
        public String getName() {
            return name;
        }

        @JsonIgnore
        public String getCountry() {
            return countryId;
        }
    }
}
