package io.bandit.limbo.limbo.modules.main.talentrole.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentRoleTitleChanged extends DomainEvent<TalentRole> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_role.title.changed";

    public TalentRoleTitleChanged(final TalentRole talentRole) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentRole));
    }

    private Payload buildPayload(final TalentRole talentRole) {

        if (!Optional.ofNullable(talentRole.getId()).isPresent()) {
            return null;
        }

        return new Payload(talentRole.getId(),talentRole.getTitle());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String talentRoleId;

        @JsonProperty(value = "type")
        private final String type = "talent_role";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String talentRoleId, String title) {
            this.talentRoleId = talentRoleId;
            this.attributes = new Attributes(title);
        }

        @Override
        public String getId() {
            return talentRoleId;
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
        private String title;

        public Attributes(String title) {
            this.title = title;
        }

        @JsonIgnore
        public String getTitle() {
            return title;
        }
    }
}
