package prioritisation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

    public static void main(String[] args) throws Exception {

        final ExecutorService producerExeccutor = Executors.newFixedThreadPool(1);
        producerExeccutor.submit(() -> {
            final ProducerRunner producerRunner = new ProducerRunner();
            producerRunner.startProducerProcess();
        });

        final ExecutorService consumerExecutor = Executors.newFixedThreadPool(1);
        consumerExecutor.submit(new ConsumerRunner());

    }

}
