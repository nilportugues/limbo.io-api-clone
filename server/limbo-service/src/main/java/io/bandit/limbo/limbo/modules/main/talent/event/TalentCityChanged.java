package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentCityChanged extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.city.changed";

    public TalentCityChanged(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),talent.getCity().getId(),talent.getCity().getName());
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

        public Payload(String cityId, String talentId, String name) {
            this.talentId = talentId;
            this.attributes = new Attributes(cityId, name);
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
