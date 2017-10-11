package io.bandit.limbo.limbo.modules.main.city.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.CityRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetCity {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetCity.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CityRepository repository;

        @Inject
        public QueryHandler(final CityRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one City by id.
         *
         * @return the City entity
         */
        public CompletableFuture<City> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
