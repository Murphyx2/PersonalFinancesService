package com.app.personalfinancesservice.domain.transaction.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteTransactionResponse {

	private boolean success;

	public DeleteTransactionResponse withSuccess(boolean success) {
		this.setSuccess(success);
		return this;
	}
}
