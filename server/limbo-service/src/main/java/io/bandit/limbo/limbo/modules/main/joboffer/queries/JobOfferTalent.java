package io.bandit.limbo.limbo.modules.main.joboffer.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class JobOfferTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("JobOfferTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final JobOfferRepository jobOfferRepository;

        @Inject
        public QueryHandler(final JobOfferRepository jobOfferRepository) {
            this.jobOfferRepository = jobOfferRepository;
        }

        /**
         * Get one JobOffer by id with Talent relationship data.
         *
         * @return the JobOffer entity
         */
        public CompletableFuture<JobOffer> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> jobOfferRepository.findOneWithTalent(query.getId()));
        }
    }
}
