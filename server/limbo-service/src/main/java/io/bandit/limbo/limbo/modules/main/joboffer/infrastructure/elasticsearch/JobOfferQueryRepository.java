package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the JobOffer entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("JobOfferQueryRepository")
public interface JobOfferQueryRepository extends ElasticsearchRepository<JobOfferQueryModel, String> {
}
