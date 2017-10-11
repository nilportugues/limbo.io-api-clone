package io.bandit.limbo.limbo.modules.main.companytraits.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class CompanyTraitsCreated extends DomainEvent<CompanyTraits> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "company_traits.created";

    public CompanyTraitsCreated(final CompanyTraits companyTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(companyTraits));
    }

    private Payload buildPayload(final CompanyTraits companyTraits) {

        if (!Optional.ofNullable(companyTraits.getId()).isPresent()) {
            return null;
        }

        String talentId = null;
        if (Optional.ofNullable(companyTraits.getTalent()).isPresent()) {
            talentId = companyTraits.getTalent().getId();
        }


        return new Payload(companyTraits.getId(), companyTraits.getTitle(), talentId);
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

        public Payload(final String companyTraitsId, final String title, final String talentId) {
            this.companyTraitsId = companyTraitsId;
            this.attributes = new Attributes(title, talentId);
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
            return attributes;
        }
    }

    public class Attributes implements DomainEvent.Attributes {

        @JsonProperty(value = "title")
        private final String title;

        @JsonProperty(value = "talent")
        private final String talentId;

        public Attributes(final String title, final String talentId) {

            this.title = title;
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getTitle() {
            return title;
        }

        @JsonIgnore
        public String getTalent() {
            return talentId;
        }
    }
}
