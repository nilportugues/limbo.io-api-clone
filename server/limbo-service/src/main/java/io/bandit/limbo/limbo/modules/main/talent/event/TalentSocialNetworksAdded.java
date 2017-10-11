package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentSocialNetworksAdded extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.social_networks.added";

    public TalentSocialNetworksAdded(final Talent talent, final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, socialNetworks));
    }

    private Payload buildPayload(final Talent talent, final SocialNetworks socialNetworks) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),socialNetworks.getId(),socialNetworks.getName(),socialNetworks.getUrl());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "type")
        private final String type = "talent";

        @JsonProperty(value = "id")
        private String talentId;

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String socialNetworksId, String talentId, String name, String url) {

            this.talentId = talentId;
            this.attributes = new Attributes(socialNetworksId, name, url);
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

        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "url")
        private String url;

        public Attributes(String socialNetworksId, String name, String url) {
            this.socialNetworksId = socialNetworksId;
            this.name = name;
            this.url = url;
        }

        @JsonIgnore
        public String getSocialNetworksId() {
            return this.socialNetworksId;
        }

        @JsonIgnore
        public String getName() {
            return this.name;
        }
        @JsonIgnore
        public String getUrl() {
            return this.url;
        }
    }
}
