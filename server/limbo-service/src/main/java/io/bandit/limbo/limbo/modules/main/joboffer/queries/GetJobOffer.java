package io.bandit.limbo.limbo.modules.main.joboffer.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetJobOffer {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetJobOffer.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final JobOfferRepository repository;

        @Inject
        public QueryHandler(final JobOfferRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one JobOffer by id.
         *
         * @return the JobOffer entity
         */
        public CompletableFuture<JobOffer> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
