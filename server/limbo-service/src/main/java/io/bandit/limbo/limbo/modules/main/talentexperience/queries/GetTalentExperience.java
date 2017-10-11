package io.bandit.limbo.limbo.modules.main.talentexperience.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.TalentExperienceRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetTalentExperience {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetTalentExperience.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentExperienceRepository repository;

        @Inject
        public QueryHandler(final TalentExperienceRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one TalentExperience by id.
         *
         * @return the TalentExperience entity
         */
        public CompletableFuture<TalentExperience> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
