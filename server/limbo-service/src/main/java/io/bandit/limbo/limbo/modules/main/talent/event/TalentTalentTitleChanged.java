package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentTalentTitleChanged extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.talent_title.changed";

    public TalentTalentTitleChanged(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),talent.getTalentTitle().getId(),talent.getTalentTitle().getTitle());
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

        public Payload(String talentTitleId, String talentId, String title) {
            this.talentId = talentId;
            this.attributes = new Attributes(talentTitleId, title);
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
        @JsonProperty(value = "talent_title_id")
        private String talentTitleId;


        @JsonProperty(value = "title")
        private String title;

        public Attributes(String talentTitleId, String title) {

            this.talentTitleId = talentTitleId;
            this.title = title;
        }

        @JsonIgnore
        public String getTalentTitleId() {
            return this.talentTitleId;
        }

        @JsonIgnore
        public String getTitle() {
            return this.title;
        }
    }
}
