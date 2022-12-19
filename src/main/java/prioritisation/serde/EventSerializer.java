package prioritisation.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EventSerializer implements Serializer<Event> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Event data) {
        try {
            if (data == null) {
                return null;
            }
            return MAPPER.writeValueAsBytes(data);
        } catch (final Exception e) {
            throw new SerializationException("An error has occured when trying to serialize the data", e);
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Event data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
