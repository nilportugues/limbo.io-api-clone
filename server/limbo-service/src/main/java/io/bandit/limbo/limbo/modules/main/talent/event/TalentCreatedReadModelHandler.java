package io.bandit.limbo.limbo.modules.main.talent.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch.TalentQueryModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch.TalentQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("TalentCreated.ReadModelHandler")
public class TalentCreatedReadModelHandler implements IEventHandler<TalentCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentQueryRepository talentQueryRepository;

    @Inject
    public TalentCreatedReadModelHandler(
            @Named("TalentQueryRepository") final TalentQueryRepository talentQueryRepository) {

        this.talentQueryRepository = talentQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentCreated event) {
        return CompletableFuture.runAsync(() -> {
            final TalentCreated.Payload payload = event.getPayload();
            final TalentCreated.Attributes attributes = payload.getAttributes();
            final TalentQueryModel talent = new TalentQueryModel();

            talent.setId(payload.getId());
            talent.setEmail(attributes.getEmail());
            talent.setPassword(attributes.getPassword());
            talent.setTalentProfile(attributes.getTalentProfile());
            talent.setTalentRole(attributes.getTalentRole());
            talent.setCountry(attributes.getCountry());
            talent.setCity(attributes.getCity());
            talent.setTalentTitle(attributes.getTalentTitle());
            talent.setTalentExperience(attributes.getTalentExperience());
            talent.setWorkType(attributes.getWorkType());
            talent.setSkills(attributes.getSkills());
            talent.setNotableProjects(attributes.getNotableProjects());
            talent.setCompanyTraits(attributes.getCompanyTraits());
            talent.setPersonalTraits(attributes.getPersonalTraits());
            talent.setSocialNetworks(attributes.getSocialNetworks());
            talent.setJobOffers(attributes.getJobOffers());

            talentQueryRepository.save(talent);
        });
    }
}
