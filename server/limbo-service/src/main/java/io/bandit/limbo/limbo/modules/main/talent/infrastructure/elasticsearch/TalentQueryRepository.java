package io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the Talent entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("TalentQueryRepository")
public interface TalentQueryRepository extends ElasticsearchRepository<TalentQueryModel, String> {
}
