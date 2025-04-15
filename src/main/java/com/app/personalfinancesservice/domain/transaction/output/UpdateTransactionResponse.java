package com.app.personalfinancesservice.domain.transaction.output;

import com.app.personalfinancesservice.domain.transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTransactionResponse {

	private Transaction transaction;

	public UpdateTransactionResponse withTransaction(Transaction transaction) {
		this.setTransaction(transaction);
		return this;
	}
}
