package io.bandit.limbo.limbo.modules.main.companytraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CompanyTraitsTalentChanged extends DomainEvent<CompanyTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "company_traits.talent.changed";

    public CompanyTraitsTalentChanged(final CompanyTraits companyTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(companyTraits));
    }

    private Payload buildPayload(final CompanyTraits companyTraits) {

        if (!Optional.ofNullable(companyTraits.getId()).isPresent()) {
            return null;
        }

        return new Payload(companyTraits.getId(),companyTraits.getTalent().getId(),companyTraits.getTalent().getEmail(),companyTraits.getTalent().getPassword());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String companyTraitsId;

        @JsonProperty(value = "type")
        private final String type = "company_traits";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String talentId, String companyTraitsId, String email, String password) {
            this.companyTraitsId = companyTraitsId;
            this.attributes = new Attributes(talentId, email, password);
        }

        @Override
        public String getId() {
            return this.companyTraitsId;
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
