package io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the NotableProjects entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("NotableProjectsQueryRepository")
public interface NotableProjectsQueryRepository extends ElasticsearchRepository<NotableProjectsQueryModel, String> {
}
