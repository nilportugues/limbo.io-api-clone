package io.bandit.limbo.limbo.infrastructure.elasticsearch;

import io.bandit.limbo.limbo.infrastructure.elasticsearch.mappers.CustomEntityMapper;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ElasticSearchModule {


    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchTemplate getElasticsearchTemplate(
            final Client client,
            final Jackson2ObjectMapperBuilder mapperBuilder) {

        return new ElasticsearchTemplate(client, getCustomEntityMapper(mapperBuilder));
    }


    private CustomEntityMapper getCustomEntityMapper(final Jackson2ObjectMapperBuilder mapperBuilder) {
        return new CustomEntityMapper(mapperBuilder.createXmlMapper(false).build());
    }
}
