package io.bandit.limbo.limbo.modules.main.personaltraits.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetPersonalTraits {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetPersonalTraits.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final PersonalTraitsRepository repository;

        @Inject
        public QueryHandler(final PersonalTraitsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one PersonalTraits by id.
         *
         * @return the PersonalTraits entity
         */
        public CompletableFuture<PersonalTraits> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
