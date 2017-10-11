package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
        import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SocialNetworksTalentRemoved extends DomainEvent<SocialNetworks> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "social_networks.talent.removed";

    public SocialNetworksTalentRemoved(final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(socialNetworks));
    }

    private Payload buildPayload(final SocialNetworks socialNetworks) {

        if (!Optional.ofNullable(socialNetworks.getId()).isPresent()) {
            return null;
        }

        return new Payload(socialNetworks.getId(),socialNetworks.getTalent().getId());
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

        public Payload(String talentId, String socialNetworksId) {
            this.socialNetworksId = socialNetworksId;
            this.attributes = new Attributes(talentId);
        }

        @Override
        public String getId() {
            return this.socialNetworksId;
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
        @JsonProperty(value = "talent_id")
        private String talentId;

        public Attributes(String talentId) {
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }
    }
}
