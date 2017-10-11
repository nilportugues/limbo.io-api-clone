package io.bandit.limbo.limbo.modules.main.country.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class CountryUpdated extends DomainEvent<Country> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "country.updated";

    public CountryUpdated(final Country country) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(country));
    }

    private Payload buildPayload(final Country country) {

        if (!Optional.ofNullable(country.getId()).isPresent()) {
            return null;
        }

        final Set<String> citiesIds = new HashSet<>();
        if (Optional.ofNullable(country.getCities()).isPresent()) {
            country.getCities().forEach(city -> citiesIds.add(city.getId()));
        }


        return new Payload(country.getId(), country.getName(), country.getCountryCode(), citiesIds);
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

        public Payload(final String countryId, final String name, final String countryCode, final Set<String> citiesIds) {
            this.countryId = countryId;
            this.attributes = new Attributes(name, countryCode, citiesIds);
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

        @JsonProperty(value = "name")
        private final String name;

        @JsonProperty(value = "country_code")
        private final String countryCode;

        @JsonProperty(value = "cities")
        private final Set<String> citiesIds;

        public Attributes(final String name, final String countryCode, final Set<String> citiesIds) {

            this.name = name;
            this.countryCode = countryCode;
            this.citiesIds = citiesIds;
        }

        @JsonIgnore
        public String getName() {
            return name;
        }
        @JsonIgnore
        public String getCountryCode() {
            return countryCode;
        }

        @JsonIgnore
        public Set<String> getCities() {
            return citiesIds;
        }
    }
}
