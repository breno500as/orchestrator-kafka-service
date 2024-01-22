package com.br.orchestrator.saga;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.br.orchestrator.config.exception.ValidationException;
import com.br.orchestrator.dto.Event;
import com.br.orchestrator.enums.TopicsEnum;

@Component
public class SagaExecutionController {

	private Logger logger = LoggerFactory.getLogger(SagaExecutionController.class);

	public TopicsEnum getTopicsEnum(Event event) {

		if (ObjectUtils.isEmpty(event.getStatus()) || ObjectUtils.isEmpty(event.getSource())) {
			throw new ValidationException("Source and status must be informed");
		}

		var topic = (TopicsEnum) Arrays.stream(SagaHandler.SAGA_HANDLER)
				.filter(row -> this.isSourceAndStatusValid(event, row)).map(i -> i[SagaHandler.TOPIC_INDEX]).findFirst()
				.orElseThrow(() -> new ValidationException("Topic not foud"));

		this.logCurrentSaga(event, topic);

		return topic;

	}

	public boolean isSourceAndStatusValid(Event e, Object[] row) {
		var source = row[SagaHandler.EVENT_SOURCE_INDEX];
		var status = row[SagaHandler.SAGA_STATUS_INDEX];
		return e.getSource().equals(source) && e.getStatus().equals(status);
	}

	private void logCurrentSaga(Event event, TopicsEnum topicsEnum) {

		var sagaId = String.format("ORDER_ID: %s | TRANSACTION_ID: %s | EVENT_ID: %s", event.getPayload().getId(),
				event.getTransactionId(), event.getId());
		var source = event.getSource();

		switch (event.getStatus()) {
		case SUCCESS -> this.logger.info("CURRENT SAGA {} | SUCCESS | NEXT TOPIC {} | {}", source, topicsEnum, sagaId);
		case ROLLBACK_PENDING ->
			this.logger.info("CURRENT SAGA {} | ROLLBACK_PENDING | NEXT TOPIC {} | {}", source, topicsEnum, sagaId);
		case FAIL -> this.logger.info("CURRENT SAGA {} | FAIL | NEXT TOPIC {} | {}", source, topicsEnum, sagaId);
		}

	}

}
