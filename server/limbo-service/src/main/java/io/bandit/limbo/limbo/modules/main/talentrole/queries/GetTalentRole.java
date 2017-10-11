package io.bandit.limbo.limbo.modules.main.talentrole.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.TalentRoleRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetTalentRole {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetTalentRole.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentRoleRepository repository;

        @Inject
        public QueryHandler(final TalentRoleRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one TalentRole by id.
         *
         * @return the TalentRole entity
         */
        public CompletableFuture<TalentRole> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
