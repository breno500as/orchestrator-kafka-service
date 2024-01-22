package com.br.orchestrator.saga;

import com.br.orchestrator.enums.EventSourceEnum;
import com.br.orchestrator.enums.SagaStatusEnum;
import com.br.orchestrator.enums.TopicsEnum;

public final class SagaHandler {

	private SagaHandler() {

	}

	public static final Object[][] SAGA_HANDLER = {

			{ EventSourceEnum.ORCHESTRATOR, SagaStatusEnum.SUCCESS, TopicsEnum.PRODUCT_VALIDATION_SUCCESS },
			{ EventSourceEnum.ORCHESTRATOR, SagaStatusEnum.FAIL, TopicsEnum.FINISH_FAIL },
			
			{ EventSourceEnum.PRODUCT_VALIDATOR_SERVICE, SagaStatusEnum.ROLLBACK_PENDING, TopicsEnum.PRODUCT_VALIDATION_FAIL },
			{ EventSourceEnum.PRODUCT_VALIDATOR_SERVICE, SagaStatusEnum.FAIL, TopicsEnum.FINISH_FAIL },
			{ EventSourceEnum.PRODUCT_VALIDATOR_SERVICE, SagaStatusEnum.SUCCESS, TopicsEnum.PAYMENT_SUCCESS },
			
			
			{ EventSourceEnum.PRODUCT_PAYMENT_SERVICE, SagaStatusEnum.ROLLBACK_PENDING, TopicsEnum.PAYMENT_FAIL },
			{ EventSourceEnum.PRODUCT_PAYMENT_SERVICE, SagaStatusEnum.FAIL, TopicsEnum.PRODUCT_VALIDATION_FAIL },
			{ EventSourceEnum.PRODUCT_PAYMENT_SERVICE, SagaStatusEnum.SUCCESS, TopicsEnum.INVENTORY_SUCCESS },
			
			
			{ EventSourceEnum.INVENTORY_SERVICE, SagaStatusEnum.ROLLBACK_PENDING, TopicsEnum.INVENTORY_FAIL },
			{ EventSourceEnum.INVENTORY_SERVICE, SagaStatusEnum.FAIL, TopicsEnum.PAYMENT_FAIL },
			{ EventSourceEnum.INVENTORY_SERVICE, SagaStatusEnum.SUCCESS, TopicsEnum.FINISH_SUCCESS },

	};
	
	
	public static final int EVENT_SOURCE_INDEX = 0;
	
	public static final int SAGA_STATUS_INDEX = 1;
	
	public static final int TOPIC_INDEX = 2;

}
