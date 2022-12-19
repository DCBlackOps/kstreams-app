package prioritisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import prioritisation.serde.Event;

import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

public class ProducerRunner {
    private static final ObjectMapper MAPPER = new ObjectMapper();


    public void startProducerProcess() {
        System.out.println("Starting the producer process");
        final String topicName = "data-cache";
        final Scanner scanner = new Scanner(System.in);
        String line;
        while((line = scanner.nextLine()) != null) {
            final String[] data = line.split(";");
            final String reference = data[0];
            final String payload = data[1];
            final String parameters = data[2];
            final Event event = new Event();
            event.setParameters(parameters);
            event.setPayload(payload);
            event.setReference(reference);
            final KafkaProducer<String, String> producer = new KafkaProducer<>(getKafkaProperties());
            final String messagePayload = convertToJson(event);
            if (messagePayload != null) {
                System.out.println("Sending message to topic : [ " + messagePayload+ " ]");
                producer.send(new ProducerRecord<>(topicName, UUID.randomUUID().toString(), messagePayload));
            } else {
                System.err.println("Cannot send message to topic (null)");
            }
        }
    }


    private String convertToJson(final Event event) {
        try {
            return MAPPER.writeValueAsString(event);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Properties getKafkaProperties() {
        System.out.println("Defining properties for Kafka");
        final Properties props = new Properties();
        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;

    }
}
