package io.bandit.limbo.limbo.modules.main.companytraits.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CompanyTraitsTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("CompanyTraitsTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CompanyTraitsRepository companyTraitsRepository;

        @Inject
        public QueryHandler(final CompanyTraitsRepository companyTraitsRepository) {
            this.companyTraitsRepository = companyTraitsRepository;
        }

        /**
         * Get one CompanyTraits by id with Talent relationship data.
         *
         * @return the CompanyTraits entity
         */
        public CompletableFuture<CompanyTraits> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> companyTraitsRepository.findOneWithTalent(query.getId()));
        }
    }
}
