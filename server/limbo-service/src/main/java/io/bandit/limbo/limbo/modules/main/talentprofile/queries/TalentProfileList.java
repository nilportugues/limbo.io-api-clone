package io.bandit.limbo.limbo.modules.main.talentprofile.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.TalentProfileRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TalentProfileList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("TalentProfileList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentProfileRepository repository;

        @Inject
        public QueryHandler(final TalentProfileRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of talentProfiles.
         *
         * @return a list of TalentProfile entities
         */
        public CompletableFuture<List<TalentProfile>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
