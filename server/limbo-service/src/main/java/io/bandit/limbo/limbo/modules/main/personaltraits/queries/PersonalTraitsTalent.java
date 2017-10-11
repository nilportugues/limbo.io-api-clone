package io.bandit.limbo.limbo.modules.main.personaltraits.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.PersonalTraitsRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class PersonalTraitsTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("PersonalTraitsTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final PersonalTraitsRepository personalTraitsRepository;

        @Inject
        public QueryHandler(final PersonalTraitsRepository personalTraitsRepository) {
            this.personalTraitsRepository = personalTraitsRepository;
        }

        /**
         * Get one PersonalTraits by id with Talent relationship data.
         *
         * @return the PersonalTraits entity
         */
        public CompletableFuture<PersonalTraits> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> personalTraitsRepository.findOneWithTalent(query.getId()));
        }
    }
}
