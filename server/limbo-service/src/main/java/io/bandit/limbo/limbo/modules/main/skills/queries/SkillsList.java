package io.bandit.limbo.limbo.modules.main.skills.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SkillsList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("SkillsList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final SkillsRepository repository;

        @Inject
        public QueryHandler(final SkillsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of skills.
         *
         * @return a list of Skills entities
         */
        public CompletableFuture<List<Skills>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
