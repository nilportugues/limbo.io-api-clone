package io.bandit.limbo.limbo.modules.main.talentexperience.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.TalentExperienceRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TalentExperienceList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("TalentExperienceList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final TalentExperienceRepository repository;

        @Inject
        public QueryHandler(final TalentExperienceRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of talentExperiences.
         *
         * @return a list of TalentExperience entities
         */
        public CompletableFuture<List<TalentExperience>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
