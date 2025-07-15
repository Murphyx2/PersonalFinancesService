package com.app.personalfinancesservice.filter;

import java.util.List;

import com.personalfinance.api.domain.transaction.TransactionType;
import com.personalfinance.api.domain.transaction.dto.TransactionDTO;

public class TransactionFilter {

	public static List<TransactionDTO> filterByTransactionType(List<TransactionDTO> transactions, TransactionType transactionType) {

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

	public static List<TransactionDTO> filterByTransactionName(List<TransactionDTO> transactions, String transactionName) {

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
