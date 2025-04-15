package com.app.personalfinancesservice.domain.transaction.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetTransactionRequest {

	private String id;
	private String userId;

	public GetTransactionRequest withId(String transactionId) {
		this.setId(transactionId);
		return this;
	}

	public GetTransactionRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
