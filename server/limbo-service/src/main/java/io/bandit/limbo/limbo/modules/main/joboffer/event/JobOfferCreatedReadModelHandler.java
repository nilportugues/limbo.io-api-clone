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


@Named("JobOfferCreated.ReadModelHandler")
public class JobOfferCreatedReadModelHandler implements IEventHandler<JobOfferCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JobOfferQueryRepository jobOfferQueryRepository;

    @Inject
    public JobOfferCreatedReadModelHandler(
            @Named("JobOfferQueryRepository") final JobOfferQueryRepository jobOfferQueryRepository) {

        this.jobOfferQueryRepository = jobOfferQueryRepository;
    }

    public CompletableFuture<Void> handle(final JobOfferCreated event) {
        return CompletableFuture.runAsync(() -> {
            final JobOfferCreated.Payload payload = event.getPayload();
            final JobOfferCreated.Attributes attributes = payload.getAttributes();
            final JobOfferQueryModel jobOffer = new JobOfferQueryModel();

            jobOffer.setId(payload.getId());
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
