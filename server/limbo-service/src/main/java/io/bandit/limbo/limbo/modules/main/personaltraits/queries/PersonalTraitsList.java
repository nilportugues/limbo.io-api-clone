package io.bandit.limbo.limbo.modules.main.personaltraits.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class PersonalTraitsList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("PersonalTraitsList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final PersonalTraitsRepository repository;

        @Inject
        public QueryHandler(final PersonalTraitsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of personalTraits.
         *
         * @return a list of PersonalTraits entities
         */
        public CompletableFuture<List<PersonalTraits>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
