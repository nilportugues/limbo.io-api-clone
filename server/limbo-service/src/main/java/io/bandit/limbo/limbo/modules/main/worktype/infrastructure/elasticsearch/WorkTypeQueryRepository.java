package io.bandit.limbo.limbo.modules.main.worktype.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the WorkType entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("WorkTypeQueryRepository")
public interface WorkTypeQueryRepository extends ElasticsearchRepository<WorkTypeQueryModel, String> {
}
