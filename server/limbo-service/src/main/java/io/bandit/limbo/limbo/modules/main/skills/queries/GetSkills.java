package io.bandit.limbo.limbo.modules.main.skills.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetSkills {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetSkills.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final SkillsRepository repository;

        @Inject
        public QueryHandler(final SkillsRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one Skills by id.
         *
         * @return the Skills entity
         */
        public CompletableFuture<Skills> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
