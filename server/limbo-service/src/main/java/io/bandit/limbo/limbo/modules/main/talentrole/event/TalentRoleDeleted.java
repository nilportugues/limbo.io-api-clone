package io.bandit.limbo.limbo.modules.main.talentrole.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class TalentRoleDeleted extends DomainEvent<TalentRole> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent_role.deleted";

    public TalentRoleDeleted(final TalentRole talentRole) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talentRole));
    }

    private Payload buildPayload(final TalentRole talentRole) {

        if (!Optional.ofNullable(talentRole.getId()).isPresent()) {
            return null;
        }

        return new Payload(talentRole.getId());
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

        public Payload(String talentRoleId) {
            this.talentRoleId = talentRoleId;
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
            return null;
        }
    }
}
