package io.bandit.limbo.limbo.modules.main.city.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the City entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("CityQueryRepository")
public interface CityQueryRepository extends ElasticsearchRepository<CityQueryModel, String> {
}
