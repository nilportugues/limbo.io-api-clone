package io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the TalentTitle entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("TalentTitleQueryRepository")
public interface TalentTitleQueryRepository extends ElasticsearchRepository<TalentTitleQueryModel, String> {
}
