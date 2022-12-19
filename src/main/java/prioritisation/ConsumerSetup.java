package prioritisation;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class ConsumerSetup {

    private static final Collection<String> topics = Collections.singletonList("data-cache");

    public Properties getKafkaProperties() {
        System.out.println("Defining consumer properties for Kafka");
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        return props;
    }


    public Consumer<String, String> startConsumer() {
        final Consumer<String, String> consumer = new KafkaConsumer(getKafkaProperties());
        consumer.subscribe(topics);
        return consumer;
    }



}
