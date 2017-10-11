package io.bandit.limbo.limbo.modules.main.talenttitle.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.TalentTitleRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetTalentTitle {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetTalentTitle.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentTitleRepository repository;

        @Inject
        public QueryHandler(final TalentTitleRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one TalentTitle by id.
         *
         * @return the TalentTitle entity
         */
        public CompletableFuture<TalentTitle> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
