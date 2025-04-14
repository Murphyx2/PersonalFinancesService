package com.app.personalfinancesservice.domain.transaction.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetTransactionRequest {

	private String transactionId;
	private String budgetId;
	private String userId;

	public GetTransactionRequest withBudgetId(String budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public GetTransactionRequest withTransactionId(String transactionId) {
		this.setTransactionId(transactionId);
		return this;
	}

	public GetTransactionRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
