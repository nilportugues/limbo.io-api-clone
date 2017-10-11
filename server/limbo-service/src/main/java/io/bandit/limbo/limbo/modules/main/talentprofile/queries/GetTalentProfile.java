package io.bandit.limbo.limbo.modules.main.talentprofile.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.TalentProfileRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetTalentProfile {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetTalentProfile.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentProfileRepository repository;

        @Inject
        public QueryHandler(final TalentProfileRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one TalentProfile by id.
         *
         * @return the TalentProfile entity
         */
        public CompletableFuture<TalentProfile> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
