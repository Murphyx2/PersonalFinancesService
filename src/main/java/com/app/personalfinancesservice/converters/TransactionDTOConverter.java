package com.app.personalfinancesservice.converters;

import java.util.ArrayList;
import java.util.List;

import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.domain.transaction.dto.TransactionDTO;

public class TransactionDTOConverter {

	public static TransactionDTO convert(Transaction transaction) {

		if (transaction == null) {
			return null;
		}

		return new TransactionDTO() //
				.withId(transaction.getId().toString()) //
				.withUserId(transaction.getUserId().toString()) //
				.withBudgetId(transaction.getBudgetId().toString()) //
				.withCategory(CategoryDTOConverter.convert(transaction.getCategory())) //
				.withCurrencyCode(transaction.getCurrencyCode()) //
				.withAmount(transaction.getAmount()) //
				.withTransactionDate(transaction.getTransactionDate()) //
				.withCreatedAt(transaction.getCreatedAt()) //
				.withUpdatedAt(transaction.getUpdatedAt()) //
				;
	}

	public static List<TransactionDTO> convertMany(List<Transaction> transactions) {
		if(transactions == null || transactions.isEmpty()) {
			return new ArrayList<>();
		}

		return transactions.stream() //
				.map(TransactionDTOConverter::convert) //
				.toList();
	}

	private TransactionDTOConverter() {
		// Empty on Purpose
	}
}
