package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.util.Optional;

public class NotableProjectsTalentAdded extends DomainEvent<NotableProjects> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "notable_projects.talent.added";

    public NotableProjectsTalentAdded(final NotableProjects notableProjects, final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(notableProjects, talent));
    }

    private Payload buildPayload(final NotableProjects notableProjects, final Talent talent) {

        if (!Optional.ofNullable(notableProjects.getId()).isPresent()) {
            return null;
        }
        return new Payload(notableProjects.getId(), talent.getId());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String notableProjectsId;

        @JsonProperty(value = "type")
        private final String type = "notable_projects";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String notableProjectsId, String talentId) {
            this.notableProjectsId = notableProjectsId;
            this.attributes = new Attributes(talentId);
        }

        @Override
        public String getId() {
            return this.notableProjectsId;
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
