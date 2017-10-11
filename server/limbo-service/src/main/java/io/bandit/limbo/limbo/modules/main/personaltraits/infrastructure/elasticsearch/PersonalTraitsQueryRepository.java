package io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * Spring Data ElasticSearch repository for the PersonalTraits entity.
 * To be used to query data. Writes should be done from incoming domain events.
 */
@Named("PersonalTraitsQueryRepository")
public interface PersonalTraitsQueryRepository extends ElasticsearchRepository<PersonalTraitsQueryModel, String> {
}
