package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.personalfinance.api.domain.transaction.dto.TransactionDTO;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

public class TransactionSorter {

	public static List<TransactionDTO> sortTransactions(List<TransactionDTO> transactions, SortBy sortBy, SortDirection sortDirection) {

		if (transactions == null || transactions.isEmpty()) {
			return transactions;
		}
		Comparator<TransactionDTO> comparator = TransactionSorter.comparator(sortBy);
		if (sortDirection.equals(SortDirection.DESC)) {
			comparator = comparator.reversed();
		}

		List<TransactionDTO> sortedTransactions = new ArrayList<>(transactions);
		sortedTransactions.sort(comparator);
		return sortedTransactions;
	}

	private static Comparator<TransactionDTO> comparator(SortBy sortBy) {
		if (sortBy == null) {
			sortBy = SortBy.CREATED_AT;
		}
		return switch (sortBy) {
			case SortBy.TRANSACTION_DATE -> Comparator.comparing(TransactionDTO::getTransactionDate);
			case SortBy.NAME -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getName());
			case TRANSACTION_TYPE -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getTransactionType());
			default -> Comparator.comparing(TransactionDTO::getCreatedAt);
		};
	}

	private TransactionSorter() {
		// Empty on purpose
	}
}
