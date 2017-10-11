package io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the TalentProfile entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("TalentProfileQueryRepository")
public interface TalentProfileQueryRepository extends ElasticsearchRepository<TalentProfileQueryModel, String> {
}
