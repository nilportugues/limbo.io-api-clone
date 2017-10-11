package io.bandit.limbo.limbo.modules.main.worktype.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeCreated;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeUpdated;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.WorkTypeRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class WorkTypePersisterService {

    private final WorkTypeRepository workTypeRepository;
    private final EventBus eventBus;

    @Inject
    public WorkTypePersisterService(final WorkTypeRepository workTypeRepository, final EventBus eventBus) {
        this.workTypeRepository = workTypeRepository;
        this.eventBus = eventBus;
    }

    public WorkType persist(final WorkType workType) throws Throwable {

        final WorkType existing = workTypeRepository.findOne(workType.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(workType);
        }

        return update(workType);
    }

    public WorkType create(final WorkType workType) throws Throwable {

        workTypeRepository.save(workType);
        eventBus.dispatch(new WorkTypeCreated(workType));

        return workType;
    }

    public WorkType update(final WorkType data) throws Throwable {

        final WorkType existing = workTypeRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final WorkType workType = updateData(data, existing);
        workTypeRepository.save(workType);

        //Dispatch changed field events.
        final List<DomainEvent> events = workType.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new WorkTypeUpdated(workType));

        return workType;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for WorkType.
    * @param workType   WorkType instance to copy data values to.
    * @return  An updated WorkType instance.
    */
    private WorkType updateData(final WorkType data, final WorkType workType) {

        workType.setWorkType(data.getWorkType());
        workType.setDescription(data.getDescription());


        return workType;
    }
}
