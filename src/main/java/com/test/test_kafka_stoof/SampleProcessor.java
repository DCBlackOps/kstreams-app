package com.test.test_kafka_stoof;

import org.apache.kafka.streams.processor.api.ContextualProcessor;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;

import java.util.Collections;
import java.util.Set;

public class SampleProcessor implements ProcessorSupplier<String, String, String, String> {

	private String storeName;
	private StoreBuilder<KeyValueStore<String, String>> dataCacheStoreBuilder;

	public SampleProcessor (final String storeName, final StoreBuilder<KeyValueStore<String, String>> dataCacheStoreBuilder) {
		this.storeName = storeName;
		this.dataCacheStoreBuilder = dataCacheStoreBuilder;
	}

	@Override
	public Processor<String, String, String, String> get() {
		return new Processor<String, String, String, String>() {

			private ProcessorContext context;
			private KeyValueStore<String, String> store;

			@Override
			public void init(ProcessorContext<String, String> context) {
				this.context = context;
				store = context.getStateStore(storeName);
			}

			@Override
			public void process(Record<String, String> record) {
				//System.out.println("key = "+record.key() + ", value = "+record.value());
				final String key = record.key();
				if (store.get(record.key()) != null) {
					System.out.println("Record exists in the key store");
				} else {
					System.out.println("Record does not exist in the store. Adding to the store");
					store.put(key, "RECORDED");
				}

			}
		};
	}

	@Override
	public Set<StoreBuilder<?>> stores() {
		return Collections.singleton(dataCacheStoreBuilder);
	}
}
