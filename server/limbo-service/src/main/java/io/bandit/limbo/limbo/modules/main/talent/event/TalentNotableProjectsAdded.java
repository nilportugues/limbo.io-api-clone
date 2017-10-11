package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentNotableProjectsAdded extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.notable_projects.added";

    public TalentNotableProjectsAdded(final Talent talent, final NotableProjects notableProjects) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent, notableProjects));
    }

    private Payload buildPayload(final Talent talent, final NotableProjects notableProjects) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),notableProjects.getId(),notableProjects.getTitle(),notableProjects.getDescription());
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

        public Payload(String notableProjectsId, String talentId, String title, String description) {

            this.talentId = talentId;
            this.attributes = new Attributes(notableProjectsId, title, description);
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

        @JsonProperty(value = "title")
        private String title;

        @JsonProperty(value = "description")
        private String description;

        public Attributes(String notableProjectsId, String title, String description) {
            this.notableProjectsId = notableProjectsId;
            this.title = title;
            this.description = description;
        }

        @JsonIgnore
        public String getNotableProjectsId() {
            return this.notableProjectsId;
        }

        @JsonIgnore
        public String getTitle() {
            return this.title;
        }
        @JsonIgnore
        public String getDescription() {
            return this.description;
        }
    }
}
