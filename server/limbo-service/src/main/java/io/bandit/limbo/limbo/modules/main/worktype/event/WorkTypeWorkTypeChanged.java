package io.bandit.limbo.limbo.modules.main.worktype.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class WorkTypeWorkTypeChanged extends DomainEvent<WorkType> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "work_type.work_type.changed";

    public WorkTypeWorkTypeChanged(final WorkType workType) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(workType));
    }

    private Payload buildPayload(final WorkType workType) {

        if (!Optional.ofNullable(workType.getId()).isPresent()) {
            return null;
        }

        return new Payload(workType.getId(),workType.getWorkType());
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String workTypeId;

        @JsonProperty(value = "type")
        private final String type = "work_type";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(String workTypeId, String workType) {
            this.workTypeId = workTypeId;
            this.attributes = new Attributes(workType);
        }

        @Override
        public String getId() {
            return workTypeId;
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

        @JsonProperty(value = "work_type")
        private String workType;

        public Attributes(String workType) {
            this.workType = workType;
        }

        @JsonIgnore
        public String getWorkType() {
            return workType;
        }
    }
}
