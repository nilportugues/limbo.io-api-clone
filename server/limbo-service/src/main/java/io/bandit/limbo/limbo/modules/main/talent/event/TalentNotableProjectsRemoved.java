package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class TalentNotableProjectsRemoved extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.notable_projects.removed";

    public TalentNotableProjectsRemoved(final Talent talent, final NotableProjects notableProjects) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, notableProjects));
    }

    private Payload buildPayload(final Talent talent, final NotableProjects notableProjects) {

        final String talentId = talent.getId();
        final String notableProjectsId = notableProjects.getId();
        if (!Optional.ofNullable(talentId).isPresent() || !Optional.ofNullable(notableProjectsId).isPresent()) {
            return null;
        }

        return new Payload(notableProjectsId, talentId);
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

        public Payload(String notableProjectsId, String talentId) {
            this.talentId = talentId;
            this.attributes = new Attributes(notableProjectsId);
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
        @JsonProperty(value = "notable_projects_id")
        private String notableProjectsId;

        public Attributes(String notableProjectsId) {
            this.notableProjectsId = notableProjectsId;
        }

        @JsonIgnore
        public String getNotableProjectsId() {
            return this.notableProjectsId;
        }
    }
}
