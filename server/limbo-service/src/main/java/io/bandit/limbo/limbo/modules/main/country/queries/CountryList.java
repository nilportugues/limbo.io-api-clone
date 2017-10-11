package io.bandit.limbo.limbo.modules.main.country.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CountryList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("CountryList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CountryRepository repository;

        @Inject
        public QueryHandler(final CountryRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of countries.
         *
         * @return a list of Country entities
         */
        public CompletableFuture<List<Country>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
