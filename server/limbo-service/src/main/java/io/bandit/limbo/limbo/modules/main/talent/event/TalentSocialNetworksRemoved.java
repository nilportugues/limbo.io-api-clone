package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class TalentSocialNetworksRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.social_networks.removed";

    public TalentSocialNetworksRemoved(final Talent talent, final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, socialNetworks));
    }

    private Payload buildPayload(final Talent talent, final SocialNetworks socialNetworks) {

        final String talentId = talent.getId();
        final String socialNetworksId = socialNetworks.getId();
        if (!Optional.ofNullable(talentId).isPresent() || !Optional.ofNullable(socialNetworksId).isPresent()) {
            return null;
        }

        return new Payload(socialNetworksId, talentId);
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

        public Payload(String socialNetworksId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(socialNetworksId);
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
        @JsonProperty(value = "social_networks_id")
        private String socialNetworksId;

        public Attributes(String socialNetworksId) {
            this.socialNetworksId = socialNetworksId;
        }

        @JsonIgnore
        public String getSocialNetworksId() {
            return this.socialNetworksId;
        }
    }
}
