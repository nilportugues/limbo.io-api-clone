package io.bandit.limbo.limbo.modules.main.country.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetCountry {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetCountry.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CountryRepository repository;

        @Inject
        public QueryHandler(final CountryRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one Country by id.
         *
         * @return the Country entity
         */
        public CompletableFuture<Country> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
