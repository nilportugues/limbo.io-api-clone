package io.bandit.limbo.limbo.modules.main.notableprojects.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class NotableProjectsTalentChanged extends DomainEvent<NotableProjects> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "notable_projects.talent.changed";

    public NotableProjectsTalentChanged(final NotableProjects notableProjects) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(notableProjects));
    }

    private Payload buildPayload(final NotableProjects notableProjects) {

        if (!Optional.ofNullable(notableProjects.getId()).isPresent()) {
            return null;
        }

        return new Payload(notableProjects.getId(),notableProjects.getTalent().getId(),notableProjects.getTalent().getEmail(),notableProjects.getTalent().getPassword());
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

        public Payload(String talentId, String notableProjectsId, String email, String password) {
            this.notableProjectsId = notableProjectsId;
            this.attributes = new Attributes(talentId, email, password);
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


        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "password")
        private String password;

        public Attributes(String talentId, String email, String password) {

            this.talentId = talentId;
            this.email = email;
            this.password = password;
        }

        @JsonIgnore
        public String getTalentId() {
            return this.talentId;
        }

        @JsonIgnore
        public String getEmail() {
            return this.email;
        }
        @JsonIgnore
        public String getPassword() {
            return this.password;
        }
    }
}
