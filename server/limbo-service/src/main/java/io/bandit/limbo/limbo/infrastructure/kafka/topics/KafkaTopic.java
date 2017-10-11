package io.bandit.limbo.limbo.infrastructure.kafka.topics;


public abstract class KafkaTopic {
    private String topic;

    public KafkaTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
