package io.bandit.limbo.limbo.modules.main.talentexperience.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceCreated;
import io.bandit.limbo.limbo.modules.main.talentexperience.event.TalentExperienceUpdated;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.TalentExperienceRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class TalentExperiencePersisterService {

    private final TalentExperienceRepository talentExperienceRepository;
    private final EventBus eventBus;

    @Inject
    public TalentExperiencePersisterService(final TalentExperienceRepository talentExperienceRepository, final EventBus eventBus) {
        this.talentExperienceRepository = talentExperienceRepository;
        this.eventBus = eventBus;
    }

    public TalentExperience persist(final TalentExperience talentExperience) throws Throwable {

        final TalentExperience existing = talentExperienceRepository.findOne(talentExperience.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(talentExperience);
        }

        return update(talentExperience);
    }

    public TalentExperience create(final TalentExperience talentExperience) throws Throwable {

        talentExperienceRepository.save(talentExperience);
        eventBus.dispatch(new TalentExperienceCreated(talentExperience));

        return talentExperience;
    }

    public TalentExperience update(final TalentExperience data) throws Throwable {

        final TalentExperience existing = talentExperienceRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final TalentExperience talentExperience = updateData(data, existing);
        talentExperienceRepository.save(talentExperience);

        //Dispatch changed field events.
        final List<DomainEvent> events = talentExperience.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new TalentExperienceUpdated(talentExperience));

        return talentExperience;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for TalentExperience.
    * @param talentExperience   TalentExperience instance to copy data values to.
    * @return  An updated TalentExperience instance.
    */
    private TalentExperience updateData(final TalentExperience data, final TalentExperience talentExperience) {

        talentExperience.setYears(data.getYears());


        return talentExperience;
    }
}
