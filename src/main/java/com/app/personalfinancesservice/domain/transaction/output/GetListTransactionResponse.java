package com.app.personalfinancesservice.domain.transaction.output;

import java.util.List;

import com.app.personalfinancesservice.domain.transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetListTransactionResponse {

	private List<Transaction> transactions;

	public GetListTransactionResponse withTransactions(List<Transaction> transactions) {
		this.setTransactions(transactions);
		return this;
	}
}
