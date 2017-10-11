package io.bandit.limbo.limbo.modules.main.worktype.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Optional;

public class WorkTypeDeleted extends DomainEvent<WorkType> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "work_type.deleted";

    public WorkTypeDeleted(final WorkType workType) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(workType));
    }

    private Payload buildPayload(final WorkType workType) {

        if (!Optional.ofNullable(workType.getId()).isPresent()) {
            return null;
        }

        return new Payload(workType.getId());
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

        public Payload(String workTypeId) {
            this.workTypeId = workTypeId;
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
            return null;
        }
    }
}
