package io.bandit.limbo.limbo.modules.main.joboffer.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch.JobOfferQueryModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch.JobOfferQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("JobOfferDeletedReadModelHandler")
public class JobOfferDeletedReadModelHandler implements IEventHandler<JobOfferDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JobOfferQueryRepository jobOfferQueryRepository;

    @Inject
    public JobOfferDeletedReadModelHandler(
            @Named("JobOfferQueryRepository") final JobOfferQueryRepository jobOfferQueryRepository) {

        this.jobOfferQueryRepository = jobOfferQueryRepository;
    }

    public CompletableFuture<Void> handle(final JobOfferDeleted event){
        return CompletableFuture.runAsync(() -> {
            final JobOfferDeleted.Payload payload = event.getPayload();
            final String jobOfferId = payload.getId();

            jobOfferQueryRepository.delete(jobOfferId);
        });
    }
}
