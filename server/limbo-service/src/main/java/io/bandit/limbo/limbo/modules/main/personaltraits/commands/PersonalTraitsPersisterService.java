package io.bandit.limbo.limbo.modules.main.personaltraits.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsCreated;
import io.bandit.limbo.limbo.modules.main.personaltraits.event.PersonalTraitsUpdated;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class PersonalTraitsPersisterService {

    private final PersonalTraitsRepository personalTraitsRepository;
    private final EventBus eventBus;

    @Inject
    public PersonalTraitsPersisterService(final PersonalTraitsRepository personalTraitsRepository, final EventBus eventBus) {
        this.personalTraitsRepository = personalTraitsRepository;
        this.eventBus = eventBus;
    }

    public PersonalTraits persist(final PersonalTraits personalTraits) throws Throwable {

        final PersonalTraits existing = personalTraitsRepository.findOne(personalTraits.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(personalTraits);
        }

        return update(personalTraits);
    }

    public PersonalTraits create(final PersonalTraits personalTraits) throws Throwable {

        personalTraitsRepository.save(personalTraits);
        eventBus.dispatch(new PersonalTraitsCreated(personalTraits));

        return personalTraits;
    }

    public PersonalTraits update(final PersonalTraits data) throws Throwable {

        final PersonalTraits existing = personalTraitsRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final PersonalTraits personalTraits = updateData(data, existing);
        personalTraitsRepository.save(personalTraits);

        //Dispatch changed field events.
        final List<DomainEvent> events = personalTraits.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new PersonalTraitsUpdated(personalTraits));

        return personalTraits;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for PersonalTraits.
    * @param personalTraits   PersonalTraits instance to copy data values to.
    * @return  An updated PersonalTraits instance.
    */
    private PersonalTraits updateData(final PersonalTraits data, final PersonalTraits personalTraits) {

        personalTraits.setDescription(data.getDescription());


        final Talent talent = data.getTalent();
        if (Optional.ofNullable(talent).isPresent()) {
            if (!Optional.ofNullable(talent.getId()).isPresent()) {
                  try {
                    talent.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            personalTraits.setTalent(talent);
        }


        return personalTraits;
    }
}
