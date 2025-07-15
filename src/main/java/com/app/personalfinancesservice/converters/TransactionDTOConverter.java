package com.app.personalfinancesservice.converters;

import java.util.ArrayList;
import java.util.List;

import com.personalfinance.api.domain.category.dto.CategoryDTO;
import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.domain.transaction.dto.TransactionDTO;
import com.personalfinance.api.domain.transaction.input.UpdateTransactionRequest;

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
				.withDescription(transaction.getDescription()) //
				.withCurrencyCode(transaction.getCurrencyCode()) //
				.withAmount(transaction.getAmount()) //
				.withTransactionDate(transaction.getTransactionDate()) //
				.withCreatedAt(transaction.getCreatedAt()) //
				.withUpdatedAt(transaction.getUpdatedAt()) //
				;
	}

	public static TransactionDTO convert(UpdateTransactionRequest request) {

		if (request == null) {
			return null;
		}

		return new TransactionDTO() //
				.withId(request.getId()) //
				.withUserId(request.getUserId()) //
				.withCategory(new CategoryDTO().withId(request.getCategoryId())) //
				.withDescription(request.getDescription()) //
				.withCurrencyCode(request.getCurrencyCode()) //
				.withAmount(request.getAmount()) //
				.withTransactionDate(request.getTransactionDate()) //
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
