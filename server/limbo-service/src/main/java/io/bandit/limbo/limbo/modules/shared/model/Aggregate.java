package io.bandit.limbo.limbo.modules.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.validation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

abstract public class Aggregate implements Cloneable, Serializable {

    @JsonIgnore
    final protected List<DomainEvent> domainEvents = new ArrayList<>();

    abstract public String getId();

    protected <T> void validate(T resource) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<T>> violations = validator.validate(resource);

        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }

    protected void apply(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> pullEvents() {
        final List<DomainEvent> domainEvents = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();

        return domainEvents;
    }
}
