package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentCompanyTraitsAdded extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.company_traits.added";

    public TalentCompanyTraitsAdded(final Talent talent, final CompanyTraits companyTraits) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, companyTraits));
    }

    private Payload buildPayload(final Talent talent, final CompanyTraits companyTraits) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),companyTraits.getId(),companyTraits.getTitle());
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

        public Payload(String companyTraitsId, String talentId, String title) {

            this.talentId = talentId;
            this.attributes = new Attributes(companyTraitsId, title);
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

        @JsonProperty(value = "title")
        private String title;

        public Attributes(String companyTraitsId, String title) {
            this.companyTraitsId = companyTraitsId;
            this.title = title;
        }

        @JsonIgnore
        public String getCompanyTraitsId() {
            return this.companyTraitsId;
        }

        @JsonIgnore
        public String getTitle() {
            return this.title;
        }
    }
}
