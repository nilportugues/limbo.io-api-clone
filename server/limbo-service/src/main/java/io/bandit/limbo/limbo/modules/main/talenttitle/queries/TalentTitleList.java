package io.bandit.limbo.limbo.modules.main.talenttitle.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.TalentTitleRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TalentTitleList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("TalentTitleList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentTitleRepository repository;

        @Inject
        public QueryHandler(final TalentTitleRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of talentTitles.
         *
         * @return a list of TalentTitle entities
         */
        public CompletableFuture<List<TalentTitle>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
