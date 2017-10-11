package io.bandit.limbo.limbo.modules.main.city.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CityNameChanged extends DomainEvent<City> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "city.name.changed";

    public CityNameChanged(final City city) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(city));
    }

    private Payload buildPayload(final City city) {

        if (!Optional.ofNullable(city.getId()).isPresent()) {
            return null;
        }

        return new Payload(city.getId(),city.getName());
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

        public Payload(String cityId, String name) {
            this.cityId = cityId;
            this.attributes = new Attributes(name);
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
        private String name;

        public Attributes(String name) {
            this.name = name;
        }

        @JsonIgnore
        public String getName() {
            return name;
        }
    }
}
