package com.app.personalfinancesservice.domain.transaction;

import java.util.Set;

public enum TransactionType {

	INCOME,  //
	EXPENSE, //
	LOAN, //
	LENDING,
	;

	public static Set<TransactionType> getTransactionTypes() {
		return Set.of(TransactionType.values());
	}

}
