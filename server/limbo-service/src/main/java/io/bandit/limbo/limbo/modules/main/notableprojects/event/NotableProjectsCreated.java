package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class NotableProjectsCreated extends DomainEvent<NotableProjects> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "notable_projects.created";

    public NotableProjectsCreated(final NotableProjects notableProjects) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(notableProjects));
    }

    private Payload buildPayload(final NotableProjects notableProjects) {

        if (!Optional.ofNullable(notableProjects.getId()).isPresent()) {
            return null;
        }

        String talentId = null;
        if (Optional.ofNullable(notableProjects.getTalent()).isPresent()) {
            talentId = notableProjects.getTalent().getId();
        }


        return new Payload(notableProjects.getId(), notableProjects.getTitle(), notableProjects.getDescription(), talentId);
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

        public Payload(final String notableProjectsId, final String title, final String description, final String talentId) {
            this.notableProjectsId = notableProjectsId;
            this.attributes = new Attributes(title, description, talentId);
        }

        @Override
        public String getId() {
            return notableProjectsId;
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

        @JsonProperty(value = "description")
        private final String description;

        @JsonProperty(value = "talent")
        private final String talentId;

        public Attributes(final String title, final String description, final String talentId) {

            this.title = title;
            this.description = description;
            this.talentId = talentId;
        }

        @JsonIgnore
        public String getTitle() {
            return title;
        }
        @JsonIgnore
        public String getDescription() {
            return description;
        }

        @JsonIgnore
        public String getTalent() {
            return talentId;
        }
    }
}
