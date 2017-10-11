package io.bandit.limbo.limbo.modules.main.talentrole.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleCreated;
import io.bandit.limbo.limbo.modules.main.talentrole.event.TalentRoleUpdated;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.TalentRoleRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class TalentRolePersisterService {

    private final TalentRoleRepository talentRoleRepository;
    private final EventBus eventBus;

    @Inject
    public TalentRolePersisterService(final TalentRoleRepository talentRoleRepository, final EventBus eventBus) {
        this.talentRoleRepository = talentRoleRepository;
        this.eventBus = eventBus;
    }

    public TalentRole persist(final TalentRole talentRole) throws Throwable {

        final TalentRole existing = talentRoleRepository.findOne(talentRole.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(talentRole);
        }

        return update(talentRole);
    }

    public TalentRole create(final TalentRole talentRole) throws Throwable {

        talentRoleRepository.save(talentRole);
        eventBus.dispatch(new TalentRoleCreated(talentRole));

        return talentRole;
    }

    public TalentRole update(final TalentRole data) throws Throwable {

        final TalentRole existing = talentRoleRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final TalentRole talentRole = updateData(data, existing);
        talentRoleRepository.save(talentRole);

        //Dispatch changed field events.
        final List<DomainEvent> events = talentRole.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new TalentRoleUpdated(talentRole));

        return talentRole;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for TalentRole.
    * @param talentRole   TalentRole instance to copy data values to.
    * @return  An updated TalentRole instance.
    */
    private TalentRole updateData(final TalentRole data, final TalentRole talentRole) {

        talentRole.setTitle(data.getTitle());
        talentRole.setDescription(data.getDescription());


        return talentRole;
    }
}
