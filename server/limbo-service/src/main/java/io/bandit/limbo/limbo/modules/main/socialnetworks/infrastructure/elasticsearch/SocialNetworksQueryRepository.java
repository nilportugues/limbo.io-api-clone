package io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the SocialNetworks entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("SocialNetworksQueryRepository")
public interface SocialNetworksQueryRepository extends ElasticsearchRepository<SocialNetworksQueryModel, String> {
}
