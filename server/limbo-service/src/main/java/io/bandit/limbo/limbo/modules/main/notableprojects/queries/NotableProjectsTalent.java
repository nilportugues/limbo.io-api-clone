package io.bandit.limbo.limbo.modules.main.notableprojects.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.NotableProjectsRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class NotableProjectsTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("NotableProjectsTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final NotableProjectsRepository notableProjectsRepository;

        @Inject
        public QueryHandler(final NotableProjectsRepository notableProjectsRepository) {
            this.notableProjectsRepository = notableProjectsRepository;
        }

        /**
         * Get one NotableProjects by id with Talent relationship data.
         *
         * @return the NotableProjects entity
         */
        public CompletableFuture<NotableProjects> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> notableProjectsRepository.findOneWithTalent(query.getId()));
        }
    }
}
