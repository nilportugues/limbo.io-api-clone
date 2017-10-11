package io.bandit.limbo.limbo.modules.main.companytraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsCreated;
import io.bandit.limbo.limbo.modules.main.companytraits.event.CompanyTraitsUpdated;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class CompanyTraitsPersisterService {

    private final CompanyTraitsRepository companyTraitsRepository;
    private final EventBus eventBus;

    @Inject
    public CompanyTraitsPersisterService(final CompanyTraitsRepository companyTraitsRepository, final EventBus eventBus) {
        this.companyTraitsRepository = companyTraitsRepository;
        this.eventBus = eventBus;
    }

    public CompanyTraits persist(final CompanyTraits companyTraits) throws Throwable {

        final CompanyTraits existing = companyTraitsRepository.findOne(companyTraits.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(companyTraits);
        }

        return update(companyTraits);
    }

    public CompanyTraits create(final CompanyTraits companyTraits) throws Throwable {

        companyTraitsRepository.save(companyTraits);
        eventBus.dispatch(new CompanyTraitsCreated(companyTraits));

        return companyTraits;
    }

    public CompanyTraits update(final CompanyTraits data) throws Throwable {

        final CompanyTraits existing = companyTraitsRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final CompanyTraits companyTraits = updateData(data, existing);
        companyTraitsRepository.save(companyTraits);

        //Dispatch changed field events.
        final List<DomainEvent> events = companyTraits.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new CompanyTraitsUpdated(companyTraits));

        return companyTraits;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for CompanyTraits.
    * @param companyTraits   CompanyTraits instance to copy data values to.
    * @return  An updated CompanyTraits instance.
    */
    private CompanyTraits updateData(final CompanyTraits data, final CompanyTraits companyTraits) {

        companyTraits.setTitle(data.getTitle());


        final Talent talent = data.getTalent();
        if (Optional.ofNullable(talent).isPresent()) {
            if (!Optional.ofNullable(talent.getId()).isPresent()) {
                  try {
                    talent.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            companyTraits.setTalent(talent);
        }


        return companyTraits;
    }
}
