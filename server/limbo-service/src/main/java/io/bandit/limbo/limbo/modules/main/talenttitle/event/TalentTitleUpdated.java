package io.bandit.limbo.limbo.modules.main.talenttitle.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class TalentTitleUpdated extends DomainEvent<TalentTitle> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_title.updated";

    public TalentTitleUpdated(final TalentTitle talentTitle) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentTitle));
    }

    private Payload buildPayload(final TalentTitle talentTitle) {

        if (!Optional.ofNullable(talentTitle.getId()).isPresent()) {
            return null;
        }


        return new Payload(talentTitle.getId(), talentTitle.getTitle());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String talentTitleId;

        @JsonProperty(value = "type")
        private final String type = "talent_title";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(final String talentTitleId, final String title) {
            this.talentTitleId = talentTitleId;
            this.attributes = new Attributes(title);
        }

        @Override
        public String getId() {
            return talentTitleId;
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

        public Attributes(final String title) {

            this.title = title;
        }

        @JsonIgnore
        public String getTitle() {
            return title;
        }
    }
}
