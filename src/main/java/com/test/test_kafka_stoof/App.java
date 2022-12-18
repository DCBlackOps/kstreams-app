package com.test.test_kafka_stoof;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

public class App {
  public static void main(String[] args) {
	  
	  final Properties properties = new Properties();
	  properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "user1-application-id");
	  properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	  properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	  properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

	  final String storeName = "data-cache-store";
	  final StoreBuilder<KeyValueStore<String, String>> dataCacheStoreBuilder = Stores.keyValueStoreBuilder(
			  Stores.persistentKeyValueStore(storeName),
			  Serdes.String(),
			  Serdes.String());




	  String[] sources = {"sample_source"};

	  Topology topology = new Topology();
	  topology.addSource("sample_source", "data-cache")
			  .addProcessor("sampleProcessor", new SampleProcessor(storeName, dataCacheStoreBuilder), sources);

	  
	  final KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);

	  kafkaStreams.setUncaughtExceptionHandler(new StreamsUncaughtExceptionHandler() {
			
		@Override
		public StreamThreadExceptionResponse handle(Throwable exception) {
			exception.printStackTrace();
			return null;
		}
	});
	  kafkaStreams.start();

  }
}
