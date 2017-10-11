package io.bandit.limbo.limbo.modules.main.companytraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class CompanyTraitsTalentAdded extends DomainEvent<CompanyTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "company_traits.talent.added";

    public CompanyTraitsTalentAdded(final CompanyTraits companyTraits, final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(companyTraits, talent));
    }

    private Payload buildPayload(final CompanyTraits companyTraits, final Talent talent) {

        if (!Optional.ofNullable(companyTraits.getId()).isPresent()) {
            return null;
        }
        return new Payload(companyTraits.getId(), talent.getId());
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

        public Payload(String companyTraitsId, String talentId) {
            this.companyTraitsId = companyTraitsId;
            this.attributes = new Attributes(talentId);
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

        public Attributes(String talentId) {
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }
    }
}
