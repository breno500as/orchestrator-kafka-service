package com.br.orchestrator.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.br.orchestrator.enums.TopicsEnum;

@Configuration
@EnableKafka
public class KafkaConfig {

	private static final Integer PARTITION_COUNT = 1;

	private static final Integer REPLICA_COUNT = 1;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;

	@Bean
	ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<String, String>(this.consumerProps());
	}

	@Bean
	ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<String, String>(this.producerProps());
	}

	@Bean
	KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

	private Map<String, Object> consumerProps() {

		var props = new HashMap<String, Object>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetReset);

		return props;
	}

	private Map<String, Object> producerProps() {

		var props = new HashMap<String, Object>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return props;
	}

	private NewTopic buildTopic(String name) {
		return TopicBuilder
				.name(name)
				.replicas(REPLICA_COUNT)
				.partitions(PARTITION_COUNT)
				.build();
	}

	@Bean
	NewTopic starSagaTopic() {
		return this.buildTopic(TopicsEnum.START_SAGA.getTopic());
	}

	@Bean
	NewTopic orchestrator() {
		return this.buildTopic(TopicsEnum.BASE_ORCHESTRATOR.getTopic());
	}

	@Bean
	NewTopic finsishSuccess() {
		return this.buildTopic(TopicsEnum.FINISH_SUCCESS.getTopic());
	}

	@Bean
	NewTopic finsishFail() {
		return this.buildTopic(TopicsEnum.FINISH_FAIL.getTopic());
	}

	@Bean
	NewTopic productValidationSuccessTopic() {
		return this.buildTopic(TopicsEnum.PRODUCT_VALIDATION_SUCCESS.getTopic());
	}

	@Bean
	NewTopic productValidationFailTopic() {
		return this.buildTopic(TopicsEnum.PRODUCT_VALIDATION_FAIL.getTopic());
	}

	@Bean
	NewTopic paymentSuccessTopic() {
		return this.buildTopic(TopicsEnum.PAYMENT_SUCCESS.getTopic());
	}

	@Bean
	NewTopic paymentFailTopic() {
		return this.buildTopic(TopicsEnum.PAYMENT_FAIL.getTopic());
	}

	@Bean
	NewTopic inventorySuccessTopic() {
		return this.buildTopic(TopicsEnum.INVENTORY_SUCCESS.getTopic());
	}

	@Bean
	NewTopic inventoryFailTopic() {
		return this.buildTopic(TopicsEnum.INVENTORY_FAIL.getTopic());
	}

}
