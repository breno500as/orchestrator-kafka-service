package com.br.orchestrator;

public enum TopicsEnum {

	START_SAGA("start-saga"),
	BASE_ORCHESTRATOR("orchestrator"),
	FINISH_SUCCESS("finish-success"),
	FINISH_FAIL("finish-fail"),
	PRODUCT_VALIDATION_SUCCESS("product-validation-success"),
	PRODUCT_VALIDATION_FAIL("product-validation-fail"),
	PAYMENT_SUCCESS("payment-success"),
	PAYMENT_FAIL("payment-fail"),
	INVENTORY_SUCCESS("inventory-success"),
	INVENTORY_FAIL("inventory-fail");

	private String topic;

	private TopicsEnum(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}

}
