package com.app.personalfinancesservice.filter;

import java.util.List;

import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.domain.transaction.TransactionType;

public class TransactionFilter {

	public static List<Transaction> filterByTransactionType(List<Transaction> transactions, TransactionType transactionType) {

		if (transactions == null || transactions.isEmpty() || transactionType == null) {
			return transactions;
		}

		return transactions.stream() //
				.filter(transaction -> transaction //
						.getCategory() //
						.getTransactionType() //
						.equals(transactionType)) //
				.toList();
	}

	public static List<Transaction> filterByTransactionName(List<Transaction> transactions, String transactionName) {

		if (transactions == null || transactions.isEmpty() || transactionName == null) {
			return transactions;
		}

		return transactions.stream() //
				.filter(transaction -> transaction //
						.getCategory() //
						.getName() //
						.contains(transactionName)) //
				.toList();
	}

	private TransactionFilter() {
		// Empty on purpose
	}

}
