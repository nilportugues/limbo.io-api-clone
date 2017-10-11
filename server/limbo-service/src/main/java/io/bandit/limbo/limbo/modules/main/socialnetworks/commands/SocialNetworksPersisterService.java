package io.bandit.limbo.limbo.modules.main.socialnetworks.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksCreated;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksUpdated;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class SocialNetworksPersisterService {

    private final SocialNetworksRepository socialNetworksRepository;
    private final EventBus eventBus;

    @Inject
    public SocialNetworksPersisterService(final SocialNetworksRepository socialNetworksRepository, final EventBus eventBus) {
        this.socialNetworksRepository = socialNetworksRepository;
        this.eventBus = eventBus;
    }

    public SocialNetworks persist(final SocialNetworks socialNetworks) throws Throwable {

        final SocialNetworks existing = socialNetworksRepository.findOne(socialNetworks.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(socialNetworks);
        }

        return update(socialNetworks);
    }

    public SocialNetworks create(final SocialNetworks socialNetworks) throws Throwable {

        socialNetworksRepository.save(socialNetworks);
        eventBus.dispatch(new SocialNetworksCreated(socialNetworks));

        return socialNetworks;
    }

    public SocialNetworks update(final SocialNetworks data) throws Throwable {

        final SocialNetworks existing = socialNetworksRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final SocialNetworks socialNetworks = updateData(data, existing);
        socialNetworksRepository.save(socialNetworks);

        //Dispatch changed field events.
        final List<DomainEvent> events = socialNetworks.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new SocialNetworksUpdated(socialNetworks));

        return socialNetworks;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for SocialNetworks.
    * @param socialNetworks   SocialNetworks instance to copy data values to.
    * @return  An updated SocialNetworks instance.
    */
    private SocialNetworks updateData(final SocialNetworks data, final SocialNetworks socialNetworks) {

        socialNetworks.setName(data.getName());
        socialNetworks.setUrl(data.getUrl());


        final Talent talent = data.getTalent();
        if (Optional.ofNullable(talent).isPresent()) {
            if (!Optional.ofNullable(talent.getId()).isPresent()) {
                  try {
                    talent.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            socialNetworks.setTalent(talent);
        }


        return socialNetworks;
    }
}
