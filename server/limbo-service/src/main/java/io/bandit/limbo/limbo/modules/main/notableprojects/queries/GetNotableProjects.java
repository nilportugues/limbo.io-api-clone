package io.bandit.limbo.limbo.modules.main.notableprojects.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetNotableProjects {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetNotableProjects.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final NotableProjectsRepository repository;

        @Inject
        public QueryHandler(final NotableProjectsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one NotableProjects by id.
         *
         * @return the NotableProjects entity
         */
        public CompletableFuture<NotableProjects> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
