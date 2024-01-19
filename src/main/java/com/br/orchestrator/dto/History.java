package com.br.orchestrator.dto;

import java.time.LocalDateTime;

import com.br.orchestrator.enums.EventSourceEnum;
import com.br.orchestrator.enums.SagaStatusEnum;

public class History {

	private EventSourceEnum source;

	private SagaStatusEnum status;

	private String message;

	private LocalDateTime createdAt;

	public History() {

	}

	public History(EventSourceEnum source, SagaStatusEnum status, String message, LocalDateTime createdAt) {
		super();
		this.source = source;
		this.status = status;
		this.message = message;
		this.createdAt = createdAt;
	}

	public EventSourceEnum getSource() {
		return source;
	}

	public void setSource(EventSourceEnum source) {
		this.source = source;
	}

	public SagaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SagaStatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
