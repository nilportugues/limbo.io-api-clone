package io.bandit.limbo.limbo.modules.main.companytraits.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CompanyTraitsList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("CompanyTraitsList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CompanyTraitsRepository repository;

        @Inject
        public QueryHandler(final CompanyTraitsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of companyTraits.
         *
         * @return a list of CompanyTraits entities
         */
        public CompletableFuture<List<CompanyTraits>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
