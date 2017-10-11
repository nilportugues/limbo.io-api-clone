package io.bandit.limbo.limbo.modules.main.talentrole.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.TalentRoleRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TalentRoleList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("TalentRoleList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentRoleRepository repository;

        @Inject
        public QueryHandler(final TalentRoleRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of talentRoles.
         *
         * @return a list of TalentRole entities
         */
        public CompletableFuture<List<TalentRole>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
