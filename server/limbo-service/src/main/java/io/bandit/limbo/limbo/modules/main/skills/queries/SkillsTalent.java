package io.bandit.limbo.limbo.modules.main.skills.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.SkillsRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SkillsTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("SkillsTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final SkillsRepository skillsRepository;

        @Inject
        public QueryHandler(final SkillsRepository skillsRepository) {
            this.skillsRepository = skillsRepository;
        }

        /**
         * Get one Skills by id with Talent relationship data.
         *
         * @return the Skills entity
         */
        public CompletableFuture<Skills> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> skillsRepository.findOneWithTalent(query.getId()));
        }
    }
}
