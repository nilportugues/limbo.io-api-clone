package io.bandit.limbo.limbo.modules.main.socialnetworks.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SocialNetworksTalentChanged extends DomainEvent<SocialNetworks> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "social_networks.talent.changed";

    public SocialNetworksTalentChanged(final SocialNetworks socialNetworks) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(socialNetworks));
    }

    private Payload buildPayload(final SocialNetworks socialNetworks) {

        if (!Optional.ofNullable(socialNetworks.getId()).isPresent()) {
            return null;
        }

        return new Payload(socialNetworks.getId(),socialNetworks.getTalent().getId(),socialNetworks.getTalent().getEmail(),socialNetworks.getTalent().getPassword());
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

        public Payload(String talentId, String socialNetworksId, String email, String password) {
            this.socialNetworksId = socialNetworksId;
            this.attributes = new Attributes(talentId, email, password);
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


        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "password")
        private String password;

        public Attributes(String talentId, String email, String password) {

            this.talentId = talentId;
            this.email = email;
            this.password = password;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }

        @JsonIgnore
        public String getEmail() {
            return this.email;
        }
        @JsonIgnore
        public String getPassword() {
            return this.password;
        }
    }
}
