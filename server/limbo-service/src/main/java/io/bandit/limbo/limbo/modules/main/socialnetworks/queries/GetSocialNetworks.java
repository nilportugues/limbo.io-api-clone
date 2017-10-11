package io.bandit.limbo.limbo.modules.main.socialnetworks.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;
import io.bandit.limbo.limbo.modules.shared.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class GetSocialNetworks {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("GetSocialNetworks.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final SocialNetworksRepository repository;

        @Inject
        public QueryHandler(final SocialNetworksRepository repository) {
            this.repository = repository;
        }

        /**
         * Get one SocialNetworks by id.
         *
         * @return the SocialNetworks entity
         */
        public CompletableFuture<SocialNetworks> handle(final Query query) {

            return CompletableFuture.supplyAsync(() -> repository.findOne(query.getId()));
        }
    }
}
