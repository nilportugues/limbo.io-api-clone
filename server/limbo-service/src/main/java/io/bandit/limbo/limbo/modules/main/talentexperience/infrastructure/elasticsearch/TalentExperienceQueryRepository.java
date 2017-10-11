package io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the TalentExperience entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("TalentExperienceQueryRepository")
public interface TalentExperienceQueryRepository extends ElasticsearchRepository<TalentExperienceQueryModel, String> {
}
