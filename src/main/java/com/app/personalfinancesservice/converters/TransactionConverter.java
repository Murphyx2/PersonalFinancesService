package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.domain.transaction.input.CreateTransactionRequest;
import com.personalfinance.api.domain.transaction.input.UpdateTransactionRequest;

public class TransactionConverter {

	private static final String TRANSACTION_LABEL = "TRANSACTION";

	public static Transaction convert(CreateTransactionRequest request) {

		return new Transaction() //
				.withBudgetId(UUIDConverter.convert(request.getBudgetId(), "budgetId", TRANSACTION_LABEL)) //
				.withUserId(UUIDConverter.convert(request.getUserId(), "userId", TRANSACTION_LABEL)) //
				.withAmount(Math.abs(request.getAmount())) //
				.withCurrencyCode(request.getCurrencyCode()) //
				.withDescription(request.getDescription()) //
				.withTransactionDate(request.getTransactionDate()) //
				.withCreatedAt(LocalDateTime.now()) //
				;
	}

	public static Transaction convert(Transaction oldTransaction, //
			Category category, //
			UpdateTransactionRequest request) {

		return oldTransaction //
				.withDescription(request.getDescription()) //
				.withAmount(request.getAmount()) //
				.withTransactionDate(request.getTransactionDate()) //
				.withCurrencyCode(request.getCurrencyCode()) //
				.withCategory(category) //
				.withUpdatedAt(LocalDateTime.now()) //
				;
	}

	private TransactionConverter() {
		// Empty on purpose
	}
}
