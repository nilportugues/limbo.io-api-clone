package io.bandit.limbo.limbo.modules.main.talenttitle.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleCreated;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleUpdated;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.TalentTitleRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class TalentTitlePersisterService {

    private final TalentTitleRepository talentTitleRepository;
    private final EventBus eventBus;

    @Inject
    public TalentTitlePersisterService(final TalentTitleRepository talentTitleRepository, final EventBus eventBus) {
        this.talentTitleRepository = talentTitleRepository;
        this.eventBus = eventBus;
    }

    public TalentTitle persist(final TalentTitle talentTitle) throws Throwable {

        final TalentTitle existing = talentTitleRepository.findOne(talentTitle.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(talentTitle);
        }

        return update(talentTitle);
    }

    public TalentTitle create(final TalentTitle talentTitle) throws Throwable {

        talentTitleRepository.save(talentTitle);
        eventBus.dispatch(new TalentTitleCreated(talentTitle));

        return talentTitle;
    }

    public TalentTitle update(final TalentTitle data) throws Throwable {

        final TalentTitle existing = talentTitleRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final TalentTitle talentTitle = updateData(data, existing);
        talentTitleRepository.save(talentTitle);

        //Dispatch changed field events.
        final List<DomainEvent> events = talentTitle.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new TalentTitleUpdated(talentTitle));

        return talentTitle;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for TalentTitle.
    * @param talentTitle   TalentTitle instance to copy data values to.
    * @return  An updated TalentTitle instance.
    */
    private TalentTitle updateData(final TalentTitle data, final TalentTitle talentTitle) {

        talentTitle.setTitle(data.getTitle());


        return talentTitle;
    }
}
