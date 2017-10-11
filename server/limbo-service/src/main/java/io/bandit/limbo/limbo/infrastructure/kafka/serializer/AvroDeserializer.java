package io.bandit.limbo.limbo.infrastructure.kafka.serializer;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private static final Logger log = LoggerFactory.getLogger(AvroDeserializer.class);
    private final Class<T> targetType;

    public AvroDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public void close() {
        // No-op
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        // No-op
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(String topic, byte[] data) {

        try {
            final Schema schema = targetType.newInstance().getSchema();
            final Injection<GenericRecord, byte[]> genericRecordInjection = GenericAvroCodecs.toBinary(schema);
            final GenericRecord genericRecord = genericRecordInjection.invert(data).get();

            return (T) SpecificData.get().deepCopy(schema, genericRecord);
        } catch (Exception e) {
            throw new SerializationException(
                    "Can't deserialize data [" + Arrays.toString(data) + "] from topic [" + topic + "]", e
            );
        }
    }
}
