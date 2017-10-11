package io.bandit.limbo.limbo.modules.main.worktype.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.WorkTypeRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetWorkType {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetWorkType.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final WorkTypeRepository repository;

        @Inject
        public QueryHandler(final WorkTypeRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one WorkType by id.
         *
         * @return the WorkType entity
         */
        public CompletableFuture<WorkType> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
