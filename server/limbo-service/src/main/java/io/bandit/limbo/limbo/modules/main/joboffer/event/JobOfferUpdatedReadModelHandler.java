package io.bandit.limbo.limbo.modules.main.joboffer.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch.JobOfferQueryModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch.JobOfferQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("JobOfferUpdated.ReadModelHandler")
public class JobOfferUpdatedReadModelHandler implements IEventHandler<JobOfferUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JobOfferQueryRepository jobOfferQueryRepository;

    @Inject
    public JobOfferUpdatedReadModelHandler(
            @Named("JobOfferQueryRepository") final JobOfferQueryRepository jobOfferQueryRepository) {

        this.jobOfferQueryRepository = jobOfferQueryRepository;
    }

    public CompletableFuture<Void> handle(final JobOfferUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final JobOfferUpdated.Payload payload = event.getPayload();
            final JobOfferUpdated.Attributes attributes = payload.getAttributes();

            final JobOfferQueryModel jobOffer = jobOfferQueryRepository.findOne(payload.getId());

            jobOffer.setTitle(attributes.getTitle());
            jobOffer.setDescription(attributes.getDescription());
            jobOffer.setSalaryMax(attributes.getSalaryMax());
            jobOffer.setSalaryMin(attributes.getSalaryMin());
            jobOffer.setSalaryCurrency(attributes.getSalaryCurrency());
            jobOffer.setTalent(attributes.getTalent());

            jobOfferQueryRepository.save(jobOffer);
        });
    }
}
