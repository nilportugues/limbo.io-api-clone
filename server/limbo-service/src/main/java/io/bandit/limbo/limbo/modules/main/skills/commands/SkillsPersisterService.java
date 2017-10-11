package io.bandit.limbo.limbo.modules.main.skills.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsCreated;
import io.bandit.limbo.limbo.modules.main.skills.event.SkillsUpdated;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class SkillsPersisterService {

    private final SkillsRepository skillsRepository;
    private final EventBus eventBus;

    @Inject
    public SkillsPersisterService(final SkillsRepository skillsRepository, final EventBus eventBus) {
        this.skillsRepository = skillsRepository;
        this.eventBus = eventBus;
    }

    public Skills persist(final Skills skills) throws Throwable {

        final Skills existing = skillsRepository.findOne(skills.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(skills);
        }

        return update(skills);
    }

    public Skills create(final Skills skills) throws Throwable {

        skillsRepository.save(skills);
        eventBus.dispatch(new SkillsCreated(skills));

        return skills;
    }

    public Skills update(final Skills data) throws Throwable {

        final Skills existing = skillsRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final Skills skills = updateData(data, existing);
        skillsRepository.save(skills);

        //Dispatch changed field events.
        final List<DomainEvent> events = skills.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new SkillsUpdated(skills));

        return skills;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for Skills.
    * @param skills   Skills instance to copy data values to.
    * @return  An updated Skills instance.
    */
    private Skills updateData(final Skills data, final Skills skills) {

        skills.setSkill(data.getSkill());


        final Talent talent = data.getTalent();
        if (Optional.ofNullable(talent).isPresent()) {
            if (!Optional.ofNullable(talent.getId()).isPresent()) {
                  try {
                    talent.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            skills.setTalent(talent);
        }


        return skills;
    }
}
