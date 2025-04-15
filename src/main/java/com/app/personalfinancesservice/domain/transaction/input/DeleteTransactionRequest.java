package com.app.personalfinancesservice.domain.transaction.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteTransactionRequest {

	private String id;
	private String userId;

	public DeleteTransactionRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public DeleteTransactionRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
