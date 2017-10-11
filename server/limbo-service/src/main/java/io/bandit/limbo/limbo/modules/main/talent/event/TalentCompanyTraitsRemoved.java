package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class TalentCompanyTraitsRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.company_traits.removed";

    public TalentCompanyTraitsRemoved(final Talent talent, final CompanyTraits companyTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, companyTraits));
    }

    private Payload buildPayload(final Talent talent, final CompanyTraits companyTraits) {

        final String talentId = talent.getId();
        final String companyTraitsId = companyTraits.getId();
        if (!Optional.ofNullable(talentId).isPresent() || !Optional.ofNullable(companyTraitsId).isPresent()) {
            return null;
        }

        return new Payload(companyTraitsId, talentId);
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

        public Payload(String companyTraitsId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(companyTraitsId);
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
        @JsonProperty(value = "company_traits_id")
        private String companyTraitsId;

        public Attributes(String companyTraitsId) {
            this.companyTraitsId = companyTraitsId;
        }

        @JsonIgnore
        public String getCompanyTraitsId() {
            return this.companyTraitsId;
        }
    }
}
