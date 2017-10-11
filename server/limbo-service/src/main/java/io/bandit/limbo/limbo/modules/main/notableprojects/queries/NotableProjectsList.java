package io.bandit.limbo.limbo.modules.main.notableprojects.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class NotableProjectsList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("NotableProjectsList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final NotableProjectsRepository repository;

        @Inject
        public QueryHandler(final NotableProjectsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of notableProjects.
         *
         * @return a list of NotableProjects entities
         */
        public CompletableFuture<List<NotableProjects>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
