package io.bandit.limbo.limbo.modules.main.talent.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentRepository repository;

        @Inject
        public QueryHandler(final TalentRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one Talent by id.
         *
         * @return the Talent entity
         */
        public CompletableFuture<Talent> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
