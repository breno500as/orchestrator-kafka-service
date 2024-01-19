package com.br.orchestrator.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.br.orchestrator.utils.JsonUtil;

@Component
public class SagaOrchestratorConsumer {

	private Logger logger = LoggerFactory.getLogger(SagaOrchestratorConsumer.class);

	@Autowired
	private JsonUtil jsonUtil;
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}", 
			topics = "${spring.kafka.topic.start-saga}"
	)
	public void consumeStartSagaEvent(String payload) {
		
		this.logger.info("Receiving event {} from notify start-saga topic", payload);
		
		var event = jsonUtil.toEvent(payload);
		
		this.logger.info(event.toString());
	}
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}", 
			topics = "${spring.kafka.topic.finish-success}"
	)
	public void consumeFinishSuccessEvent(String payload) {
		
		this.logger.info("Receiving event {} from notify finish-succes topic", payload);
		
		var event = jsonUtil.toEvent(payload);
		
		this.logger.info(event.toString());
	}
	
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}", 
			topics = "${spring.kafka.topic.finish-fail}"
	)
	public void consumeFinishFailEvent(String payload) {
		
		this.logger.info("Receiving event {} from notify finish-fail topic", payload);
		
		var event = jsonUtil.toEvent(payload);
		
		this.logger.info(event.toString());
	}
	
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}", 
			topics = "${spring.kafka.topic.orchestrator}"
	)
	public void consumeOrchestratorEvent(String payload) {
		
		this.logger.info("Receiving event {} from notify orchestrator topic", payload);
		
		var event = jsonUtil.toEvent(payload);
		
		this.logger.info(event.toString());
	}

}
