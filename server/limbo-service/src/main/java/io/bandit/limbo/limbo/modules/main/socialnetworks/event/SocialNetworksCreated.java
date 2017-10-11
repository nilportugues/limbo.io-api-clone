package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class SocialNetworksCreated extends DomainEvent<SocialNetworks> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "social_networks.created";

    public SocialNetworksCreated(final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(socialNetworks));
    }

    private Payload buildPayload(final SocialNetworks socialNetworks) {

        if (!Optional.ofNullable(socialNetworks.getId()).isPresent()) {
            return null;
        }

        String talentId = null;
        if (Optional.ofNullable(socialNetworks.getTalent()).isPresent()) {
            talentId = socialNetworks.getTalent().getId();
        }


        return new Payload(socialNetworks.getId(), socialNetworks.getName(), socialNetworks.getUrl(), talentId);
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

        public Payload(final String socialNetworksId, final String name, final String url, final String talentId) {
            this.socialNetworksId = socialNetworksId;
            this.attributes = new Attributes(name, url, talentId);
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

        @JsonProperty(value = "name")
        private final String name;

        @JsonProperty(value = "url")
        private final String url;

        @JsonProperty(value = "talent")
        private final String talentId;

        public Attributes(final String name, final String url, final String talentId) {

            this.name = name;
            this.url = url;
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getName() {
            return name;
        }
        @JsonIgnore
        public String getUrl() {
            return url;
        }

        @JsonIgnore
        public String getTalent() {
            return talentId;
        }
    }
}
