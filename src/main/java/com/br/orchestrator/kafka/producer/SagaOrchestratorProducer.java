package com.br.orchestrator.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.br.orchestrator.enums.TopicsEnum;

@Component
public class SagaOrchestratorProducer {

	private Logger logger = LoggerFactory.getLogger(SagaOrchestratorProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

 

	public void sendEvent(String payload, TopicsEnum topic) {

		try {

			this.logger.info("Send event to topic {} with data {}", topic.getTopic(), payload);
			this.kafkaTemplate.send(topic.getTopic(), payload);

		} catch (Exception e) {
			this.logger.error("Error trying to send data {} to topic {}", topic.getTopic(), payload, e);
		}

	}

}
