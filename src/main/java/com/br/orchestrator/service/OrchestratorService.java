package com.br.orchestrator.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.orchestrator.dto.Event;
import com.br.orchestrator.dto.History;
import com.br.orchestrator.enums.EventSourceEnum;
import com.br.orchestrator.enums.SagaStatusEnum;
import com.br.orchestrator.enums.TopicsEnum;
import com.br.orchestrator.kafka.producer.SagaOrchestratorProducer;
import com.br.orchestrator.saga.SagaExecutionController;
import com.br.orchestrator.utils.JsonUtil;

@Service
public class OrchestratorService {

	private Logger logger = LoggerFactory.getLogger(OrchestratorService.class);

	@Autowired
	private SagaOrchestratorProducer sagaProducer;

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	private SagaExecutionController sagaExecutionController;

	public void startSaga(Event event) {
		
		event.setSource(EventSourceEnum.ORCHESTRATOR);
		event.setStatus(SagaStatusEnum.SUCCESS);
		
		var topic = this.sagaExecutionController.getTopicsEnum(event);
		
		this.addHistory(event, "Saga started");
		
		this.logger.info("SAGA STARTED");
		
		this.sagaProducer.sendEvent(this.jsonUtil.toJson(event), topic);

	}

	public void finishSagaSuccess(Event event) {
		
		event.setSource(EventSourceEnum.ORCHESTRATOR);
		event.setStatus(SagaStatusEnum.SUCCESS);
				
		this.addHistory(event, "Saga finished succesfully!");
		
		this.logger.info("SAGA FINISHED SUCCESSFULLY");
		
		this.notifyFinishedSaga(event);

	}

	public void finishSagaFail(Event event) {
		
		event.setSource(EventSourceEnum.ORCHESTRATOR);
		event.setStatus(SagaStatusEnum.FAIL);
				
		this.addHistory(event, "Saga finished fail!");
		
		this.logger.info("SAGA FINISHED FAIL");
		
		this.notifyFinishedSaga(event);

	}

	public void continueSaga(Event event) {

		var topic = this.sagaExecutionController.getTopicsEnum(event);
		
		this.logger.info("SAGA CONTINUING FOR EVENT: {}", event.getId());
		
		this.sagaProducer.sendEvent(this.jsonUtil.toJson(event), topic);
		
	}
	
	private void addHistory(Event event, String message) {

		var h = new History();
		h.setCreatedAt(LocalDateTime.now());
		h.setSource(event.getSource());
		h.setStatus(event.getStatus());
		h.setMessage(message);

		event.addToHistory(h);
	}
	
	private void notifyFinishedSaga(Event event) {
		this.sagaProducer.sendEvent(this.jsonUtil.toJson(event), TopicsEnum.NOTIFY_ENDING);
	}

}
