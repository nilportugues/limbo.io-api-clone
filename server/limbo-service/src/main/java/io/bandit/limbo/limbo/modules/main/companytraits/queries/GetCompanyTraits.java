package io.bandit.limbo.limbo.modules.main.companytraits.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.CompanyTraitsRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetCompanyTraits {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetCompanyTraits.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final CompanyTraitsRepository repository;

        @Inject
        public QueryHandler(final CompanyTraitsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one CompanyTraits by id.
         *
         * @return the CompanyTraits entity
         */
        public CompletableFuture<CompanyTraits> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
