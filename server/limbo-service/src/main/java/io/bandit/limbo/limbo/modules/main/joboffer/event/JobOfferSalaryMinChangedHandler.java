package io.bandit.limbo.limbo.modules.main.joboffer.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch.JobOfferQueryModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch.JobOfferQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("JobOfferSalaryMinChanged.Handler")
public class JobOfferSalaryMinChangedHandler implements IEventHandler<JobOfferSalaryMinChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JobOfferQueryRepository jobOfferQueryRepository;

    @Inject
    public JobOfferSalaryMinChangedHandler(@Named("JobOfferQueryRepository") final JobOfferQueryRepository jobOfferQueryRepository) {

        this.jobOfferQueryRepository = jobOfferQueryRepository;
    }

    public CompletableFuture<Void> handle(final JobOfferSalaryMinChanged event) {

        return CompletableFuture.runAsync(() -> {
            final JobOfferSalaryMinChanged.Payload payload = event.getPayload();
            final JobOfferSalaryMinChanged.Attributes attributes = payload.getAttributes();
            final String jobOfferId = payload.getId();

            //Custom logic here.

        });
    }
}
