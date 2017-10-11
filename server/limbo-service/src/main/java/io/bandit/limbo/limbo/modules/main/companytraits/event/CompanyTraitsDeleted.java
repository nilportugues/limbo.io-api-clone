package io.bandit.limbo.limbo.modules.main.companytraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class CompanyTraitsDeleted extends DomainEvent<CompanyTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "company_traits.deleted";

    public CompanyTraitsDeleted(final CompanyTraits companyTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(companyTraits));
    }

    private Payload buildPayload(final CompanyTraits companyTraits) {

        if (!Optional.ofNullable(companyTraits.getId()).isPresent()) {
            return null;
        }

        return new Payload(companyTraits.getId());
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

        public Payload(String companyTraitsId) {
            this.companyTraitsId = companyTraitsId;
        }

        @Override
        public String getId() {
            return companyTraitsId;
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
