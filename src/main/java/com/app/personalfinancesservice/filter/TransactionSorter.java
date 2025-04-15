package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.transaction.Transaction;

public class TransactionSorter {

	public static List<Transaction> sortTransactions(List<Transaction> transactions, SortBy sortBy, SortDirection sortDirection) {

		if (transactions == null || transactions.isEmpty()) {
			return transactions;
		}
		Comparator<Transaction> comparator = TransactionSorter.comparator(sortBy);
		if (sortDirection.equals(SortDirection.DESC)) {
			comparator = comparator.reversed();
		}

		List<Transaction> sortedTransactions = new ArrayList<>(transactions);
		sortedTransactions.sort(comparator);
		return sortedTransactions;
	}

	private static Comparator<Transaction> comparator(SortBy sortBy) {
		if (sortBy == null) {
			sortBy = SortBy.CREATED_AT;
		}
		return switch (sortBy) {
			case SortBy.TRANSACTION_DATE -> Comparator.comparing(Transaction::getTransactionDate);
			case SortBy.NAME -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getName());
			case TRANSACTION_TYPE -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getTransactionType());
			default -> Comparator.comparing(Transaction::getCreatedAt);
		};
	}

	private TransactionSorter() {
		// Empty on purpose
	}
}
