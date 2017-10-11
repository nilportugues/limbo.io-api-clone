package io.bandit.limbo.limbo.infrastructure.kafka.config;

import io.bandit.limbo.limbo.infrastructure.kafka.serializer.AvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConfiguration {
    private final Map<String, Object> props = new HashMap<>();

    public KafkaConfiguration(final String bootstrapServers) {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
    }

    public Map<String, Object> getProps() {
        return props;
    }
}
