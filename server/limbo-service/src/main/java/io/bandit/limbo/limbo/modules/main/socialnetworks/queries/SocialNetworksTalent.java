package io.bandit.limbo.limbo.modules.main.socialnetworks.queries;

import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQuery;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryHandler;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.SocialNetworksRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SocialNetworksTalent {

    public static class Query implements IQuery {
        private final String id;

        public Query(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    @Named("SocialNetworksTalent.QueryHandler")
    public static class QueryHandler implements IQueryHandler<Query> {
        private final SocialNetworksRepository socialNetworksRepository;

        @Inject
        public QueryHandler(final SocialNetworksRepository socialNetworksRepository) {
            this.socialNetworksRepository = socialNetworksRepository;
        }

        /**
         * Get one SocialNetworks by id with Talent relationship data.
         *
         * @return the SocialNetworks entity
         */
        public CompletableFuture<SocialNetworks> handle(final Query query) {
            return CompletableFuture.supplyAsync(() -> socialNetworksRepository.findOneWithTalent(query.getId()));
        }
    }
}
