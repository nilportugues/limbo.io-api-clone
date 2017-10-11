package io.bandit.limbo.limbo.modules.main.worktype.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.WorkTypeRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class WorkTypeList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("WorkTypeList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final WorkTypeRepository repository;

        @Inject
        public QueryHandler(final WorkTypeRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of workTypes.
         *
         * @return a list of WorkType entities
         */
        public CompletableFuture<List<WorkType>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
