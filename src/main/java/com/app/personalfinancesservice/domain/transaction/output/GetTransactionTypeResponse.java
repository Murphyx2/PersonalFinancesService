package com.app.personalfinancesservice.domain.transaction.output;

import java.util.Set;

import com.app.personalfinancesservice.domain.transaction.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetTransactionTypeResponse {

	private Set<TransactionType> transactionType;

	public GetTransactionTypeResponse withTransactionType(Set<TransactionType> transactionTypeSet) {
		this.setTransactionType(transactionTypeSet);
		return this;
	}
}
