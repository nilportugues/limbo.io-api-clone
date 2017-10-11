package io.bandit.limbo.limbo.modules.main.country.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the Country entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("CountryQueryRepository")
public interface CountryQueryRepository extends ElasticsearchRepository<CountryQueryModel, String> {
}
