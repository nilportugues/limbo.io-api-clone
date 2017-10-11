package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TalentWorkTypeChanged extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.work_type.changed";

    public TalentWorkTypeChanged(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        return new Payload(talent.getId(),talent.getWorkType().getId(),talent.getWorkType().getWorkType(),talent.getWorkType().getDescription());
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

        public Payload(String workTypeId, String talentId, String workType, String description) {
            this.talentId = talentId;
            this.attributes = new Attributes(workTypeId, workType, description);
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
        @JsonProperty(value = "work_type_id")
        private String workTypeId;


        @JsonProperty(value = "work_type")
        private String workType;

        @JsonProperty(value = "description")
        private String description;

        public Attributes(String workTypeId, String workType, String description) {

            this.workTypeId = workTypeId;
            this.workType = workType;
            this.description = description;
        }

        @JsonIgnore
        public String getWorkTypeId() {
            return this.workTypeId;
        }

        @JsonIgnore
        public String getWorkType() {
            return this.workType;
        }
        @JsonIgnore
        public String getDescription() {
            return this.description;
        }
    }
}
