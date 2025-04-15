package com.app.personalfinancesservice.domain.transaction.output;

import com.app.personalfinancesservice.domain.transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionResponse {

	private Transaction transaction;

	public CreateTransactionResponse withTransaction(Transaction transaction) {
		this.setTransaction(transaction);
		return this;
	}
}
