package io.bandit.limbo.limbo.modules.main.skills.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the Skills entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("SkillsQueryRepository")
public interface SkillsQueryRepository extends ElasticsearchRepository<SkillsQueryModel, String> {
}
