package io.bandit.limbo.limbo.infrastructure.kafka;

import io.bandit.limbo.limbo.infrastructure.kafka.config.KafkaConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaModule {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.topics.user-blocked}")
    private String userBlockedTopic;

    @Bean
    public KafkaConfiguration getKafkaConfiguration() {
        return new KafkaConfiguration(bootstrapServers);
    }
}
