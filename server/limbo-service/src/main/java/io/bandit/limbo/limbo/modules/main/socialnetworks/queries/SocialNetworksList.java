package io.bandit.limbo.limbo.modules.main.socialnetworks.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SocialNetworksList {

    public static class Query implements IQuery {
        private final FilterOptions filters;

        public Query(final FilterOptions filters) {
            this.filters = filters;
        }

        public FilterOptions getFilters() {
            return this.filters;
        }
    }

    @Named("SocialNetworksList.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final SocialNetworksRepository repository;

        @Inject
        public QueryHandler(final SocialNetworksRepository repository) {
            this.repository = repository;
        }

        /**
         * Get a list of socialNetworks.
         *
         * @return a list of SocialNetworks entities
         */
        public CompletableFuture<List<SocialNetworks>> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findAll(query.getFilters()));
        }
    }
}
