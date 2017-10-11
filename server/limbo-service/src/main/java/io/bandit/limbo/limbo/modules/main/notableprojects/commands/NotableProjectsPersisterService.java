package io.bandit.limbo.limbo.modules.main.notableprojects.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsCreated;
import io.bandit.limbo.limbo.modules.main.notableprojects.event.NotableProjectsUpdated;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class NotableProjectsPersisterService {

    private final NotableProjectsRepository notableProjectsRepository;
    private final EventBus eventBus;

    @Inject
    public NotableProjectsPersisterService(final NotableProjectsRepository notableProjectsRepository, final EventBus eventBus) {
        this.notableProjectsRepository = notableProjectsRepository;
        this.eventBus = eventBus;
    }

    public NotableProjects persist(final NotableProjects notableProjects) throws Throwable {

        final NotableProjects existing = notableProjectsRepository.findOne(notableProjects.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(notableProjects);
        }

        return update(notableProjects);
    }

    public NotableProjects create(final NotableProjects notableProjects) throws Throwable {

        notableProjectsRepository.save(notableProjects);
        eventBus.dispatch(new NotableProjectsCreated(notableProjects));

        return notableProjects;
    }

    public NotableProjects update(final NotableProjects data) throws Throwable {

        final NotableProjects existing = notableProjectsRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final NotableProjects notableProjects = updateData(data, existing);
        notableProjectsRepository.save(notableProjects);

        //Dispatch changed field events.
        final List<DomainEvent> events = notableProjects.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new NotableProjectsUpdated(notableProjects));

        return notableProjects;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for NotableProjects.
    * @param notableProjects   NotableProjects instance to copy data values to.
    * @return  An updated NotableProjects instance.
    */
    private NotableProjects updateData(final NotableProjects data, final NotableProjects notableProjects) {

        notableProjects.setTitle(data.getTitle());
        notableProjects.setDescription(data.getDescription());


        final Talent talent = data.getTalent();
        if (Optional.ofNullable(talent).isPresent()) {
            if (!Optional.ofNullable(talent.getId()).isPresent()) {
                  try {
                    talent.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            notableProjects.setTalent(talent);
        }


        return notableProjects;
    }
}
