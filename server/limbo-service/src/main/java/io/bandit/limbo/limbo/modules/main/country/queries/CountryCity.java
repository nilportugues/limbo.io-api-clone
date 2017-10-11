package io.bandit.limbo.limbo.modules.main.country.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.CountryRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CountryCity {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("CountryCity.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CountryRepository countryRepository;

        @Inject
        public QueryHandler(final CountryRepository countryRepository) {
            this.countryRepository = countryRepository;
        }

       /**
         * Get one Country by id with City relationship data.
         *
         * @return the Country entity
         */
        public CompletableFuture<Country> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> countryRepository.findOneWithCities(query.getId()));
        }
    }
}
