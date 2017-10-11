package io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the TalentRole entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("TalentRoleQueryRepository")
public interface TalentRoleQueryRepository extends ElasticsearchRepository<TalentRoleQueryModel, String> {
}
