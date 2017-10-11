package io.bandit.limbo.limbo.infrastructure.kafka.topics;


import io.bandit.limbo.limbo.infrastructure.kafka.config.KafkaConfiguration;
import io.bandit.limbo.limbo.infrastructure.kafka.serializer.AvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class KafkaConsumerConfig<T1 extends KafkaTopic, T2> {

    private HashMap<String, Object> consumerProps;
    private List<String> topics = new ArrayList<>();

    @Inject
    public KafkaConsumerConfig(final KafkaConfiguration configuration, final T1 topic) {
        buildTopic(topic);
        buildConfiguration(configuration);
    }

    private void buildConfiguration(final KafkaConfiguration configuration) {
        consumerProps = new HashMap<>(configuration.getProps());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, getClassName().toString());
    }

    private void buildTopic(final T1 topic) {
        this.topics.add(topic.getTopic());
    }

    @SuppressWarnings("unchecked")
    public KafkaConsumer<String, T2> getKafkaConsumer() {
        return new KafkaConsumer<>(consumerProps, new StringDeserializer(), new AvroDeserializer(getClassName()));
    }

    public List<String> getTopics() {
        return topics;
    }

    protected abstract Class<T2> getClassName();
}
