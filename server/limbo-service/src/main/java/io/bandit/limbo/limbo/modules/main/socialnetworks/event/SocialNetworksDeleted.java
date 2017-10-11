package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class SocialNetworksDeleted extends DomainEvent<SocialNetworks> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "social_networks.deleted";

    public SocialNetworksDeleted(final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(socialNetworks));
    }

    private Payload buildPayload(final SocialNetworks socialNetworks) {

        if (!Optional.ofNullable(socialNetworks.getId()).isPresent()) {
            return null;
        }

        return new Payload(socialNetworks.getId());
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

        public Payload(String socialNetworksId) {
            this.socialNetworksId = socialNetworksId;
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
            return null;
        }
    }
}
