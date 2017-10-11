package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class NotableProjectsDescriptionChanged extends DomainEvent<NotableProjects> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "notable_projects.description.changed";

    public NotableProjectsDescriptionChanged(final NotableProjects notableProjects) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(notableProjects));
    }

    private Payload buildPayload(final NotableProjects notableProjects) {

        if (!Optional.ofNullable(notableProjects.getId()).isPresent()) {
            return null;
        }

        return new Payload(notableProjects.getId(),notableProjects.getDescription());
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

        public Payload(String notableProjectsId, String description) {
            this.notableProjectsId = notableProjectsId;
            this.attributes = new Attributes(description);
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

        @JsonProperty(value = "description")
        private String description;

        public Attributes(String description) {
            this.description = description;
        }

        @JsonIgnore
        public String getDescription() {
            return description;
        }
    }
}
