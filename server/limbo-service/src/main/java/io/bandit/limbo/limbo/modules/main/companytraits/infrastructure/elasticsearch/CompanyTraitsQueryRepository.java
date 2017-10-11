package io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the CompanyTraits entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("CompanyTraitsQueryRepository")
public interface CompanyTraitsQueryRepository extends ElasticsearchRepository<CompanyTraitsQueryModel, String> {
}
