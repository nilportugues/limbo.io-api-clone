package io.bandit.limbo.limbo.modules.main.joboffer.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferCreated;
import io.bandit.limbo.limbo.modules.main.joboffer.event.JobOfferUpdated;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class JobOfferPersisterService {

    private final JobOfferRepository jobOfferRepository;
    private final EventBus eventBus;

    @Inject
    public JobOfferPersisterService(final JobOfferRepository jobOfferRepository, final EventBus eventBus) {
        this.jobOfferRepository = jobOfferRepository;
        this.eventBus = eventBus;
    }

    public JobOffer persist(final JobOffer jobOffer) throws Throwable {

        final JobOffer existing = jobOfferRepository.findOne(jobOffer.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(jobOffer);
        }

        return update(jobOffer);
    }

    public JobOffer create(final JobOffer jobOffer) throws Throwable {

        jobOfferRepository.save(jobOffer);
        eventBus.dispatch(new JobOfferCreated(jobOffer));

        return jobOffer;
    }

    public JobOffer update(final JobOffer data) throws Throwable {

        final JobOffer existing = jobOfferRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final JobOffer jobOffer = updateData(data, existing);
        jobOfferRepository.save(jobOffer);

        //Dispatch changed field events.
        final List<DomainEvent> events = jobOffer.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new JobOfferUpdated(jobOffer));

        return jobOffer;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for JobOffer.
    * @param jobOffer   JobOffer instance to copy data values to.
    * @return  An updated JobOffer instance.
    */
    private JobOffer updateData(final JobOffer data, final JobOffer jobOffer) {

        jobOffer.setTitle(data.getTitle());
        jobOffer.setDescription(data.getDescription());
        jobOffer.setSalaryMax(data.getSalaryMax());
        jobOffer.setSalaryMin(data.getSalaryMin());
        jobOffer.setSalaryCurrency(data.getSalaryCurrency());


        final Talent talent = data.getTalent();
        if (Optional.ofNullable(talent).isPresent()) {
            if (!Optional.ofNullable(talent.getId()).isPresent()) {
                  try {
                    talent.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            jobOffer.setTalent(talent);
        }


        return jobOffer;
    }
}
