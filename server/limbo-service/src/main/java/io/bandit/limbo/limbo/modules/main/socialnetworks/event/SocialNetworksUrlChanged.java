package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SocialNetworksUrlChanged extends DomainEvent<SocialNetworks> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "social_networks.url.changed";

    public SocialNetworksUrlChanged(final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(socialNetworks));
    }

    private Payload buildPayload(final SocialNetworks socialNetworks) {

        if (!Optional.ofNullable(socialNetworks.getId()).isPresent()) {
            return null;
        }

        return new Payload(socialNetworks.getId(),socialNetworks.getUrl());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String socialNetworksId;

        @JsonProperty(value = "type")
        private final String type = "social_networks";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String socialNetworksId, String url) {
            this.socialNetworksId = socialNetworksId;
            this.attributes = new Attributes(url);
        }

        @Override
        public String getId() {
            return socialNetworksId;
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

        @JsonProperty(value = "url")
        private String url;

        public Attributes(String url) {
            this.url = url;
        }

        @JsonIgnore
        public String getUrl() {
            return url;
        }
    }
}
