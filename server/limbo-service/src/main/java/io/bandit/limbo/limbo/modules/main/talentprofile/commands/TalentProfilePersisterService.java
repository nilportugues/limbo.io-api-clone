package io.bandit.limbo.limbo.modules.main.talentprofile.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileCreated;
import io.bandit.limbo.limbo.modules.main.talentprofile.event.TalentProfileUpdated;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.TalentProfileRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class TalentProfilePersisterService {

    private final TalentProfileRepository talentProfileRepository;
    private final EventBus eventBus;

    @Inject
    public TalentProfilePersisterService(final TalentProfileRepository talentProfileRepository, final EventBus eventBus) {
        this.talentProfileRepository = talentProfileRepository;
        this.eventBus = eventBus;
    }

    public TalentProfile persist(final TalentProfile talentProfile) throws Throwable {

        final TalentProfile existing = talentProfileRepository.findOne(talentProfile.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(talentProfile);
        }

        return update(talentProfile);
    }

    public TalentProfile create(final TalentProfile talentProfile) throws Throwable {

        talentProfileRepository.save(talentProfile);
        eventBus.dispatch(new TalentProfileCreated(talentProfile));

        return talentProfile;
    }

    public TalentProfile update(final TalentProfile data) throws Throwable {

        final TalentProfile existing = talentProfileRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final TalentProfile talentProfile = updateData(data, existing);
        talentProfileRepository.save(talentProfile);

        //Dispatch changed field events.
        final List<DomainEvent> events = talentProfile.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new TalentProfileUpdated(talentProfile));

        return talentProfile;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for TalentProfile.
    * @param talentProfile   TalentProfile instance to copy data values to.
    * @return  An updated TalentProfile instance.
    */
    private TalentProfile updateData(final TalentProfile data, final TalentProfile talentProfile) {

        talentProfile.setFirstName(data.getFirstName());
        talentProfile.setLastName(data.getLastName());


        return talentProfile;
    }
}
