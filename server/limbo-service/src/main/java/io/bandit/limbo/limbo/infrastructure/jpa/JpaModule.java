package io.bandit.limbo.limbo.infrastructure.jpa;

import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;
import io.bandit.limbo.limbo.infrastructure.jpa.repository.jpa.JpaConversion;
import io.bandit.limbo.limbo.infrastructure.jpa.repository.jpa.JpaFilteringProvider;
import io.bandit.limbo.limbo.infrastructure.jpa.repository.elasticsearch.ElasticSearchFilterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaModule {
    @Bean
    public ElasticSearchFilterAdapter getAdapter() {
        return new ElasticSearchFilterAdapter();
    }

    @Bean
    public JpaFilteringProvider getJpaFilteringProvider(final TypeConverter typeConverter) {
        return new JpaFilteringProvider(typeConverter);
    }

    @Bean
    public JpaConversion getJpaConversion(final JpaFilteringProvider provider) {
        return new JpaConversion(provider);
    }
}
