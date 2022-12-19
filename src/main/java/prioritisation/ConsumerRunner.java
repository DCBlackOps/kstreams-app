package prioritisation;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class ConsumerRunner implements Runnable{

    private ConsumerSetup consumerSetup;

    public ConsumerRunner() {
        consumerSetup = new ConsumerSetup();
    }

    @Override
    public void run() {
        System.out.println("Starting the consumer...");
        final Consumer<String, String> consumer = consumerSetup.startConsumer();
        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
            }
        }
    }
}
