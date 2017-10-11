package io.bandit.limbo.limbo.modules.shared.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEvent;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

abstract public class DomainEvent<T> implements Serializable, IEvent {
    private static final String OCCURRED_ON = "occurred_on";
    private static final String PAYLOAD = "data";
    private static final String EVENT_TYPE = "event_type";
    private static final String EVENT_VERSION = "event_version";
    private static final String EVENT_ID = "event_id";

    @NotNull
    @JsonProperty(value = EVENT_ID)
    final private String id = UUID.randomUUID().toString();

    @NotNull
    @JsonProperty(value = EVENT_VERSION)
    protected Integer version;

    @NotNull
    @JsonProperty(value = EVENT_TYPE)
    protected String type;

    @NotNull
    @JsonProperty(value = PAYLOAD)
    protected Payload payload;

    @NotNull
    @JsonProperty(value = OCCURRED_ON)
    final private String occurredOn = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);

    protected void setVersion(Integer version) {
        this.version = version;
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public Payload getPayload() {
        return payload;
    }

    public String getOccurredOn() {
        return occurredOn;
    }

    public interface Payload extends Serializable {
        String getId();
        String getType();
        Attributes getAttributes();
    }

    public interface Attributes extends Serializable {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DomainEvent event = (DomainEvent) o;

        return id.equals(event.id) && type.equals(event.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
