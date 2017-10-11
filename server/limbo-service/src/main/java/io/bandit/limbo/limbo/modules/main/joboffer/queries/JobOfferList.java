package io.bandit.limbo.limbo.modules.main.joboffer.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.JobOfferRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class JobOfferList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("JobOfferList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final JobOfferRepository repository;

        @Inject
        public QueryHandler(final JobOfferRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of jobOffers.
         *
         * @return a list of JobOffer entities
         */
        public CompletableFuture<List<JobOffer>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
