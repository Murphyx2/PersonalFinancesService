package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;

import com.app.personalfinancesservice.domain.transaction.Transaction;
import com.app.personalfinancesservice.domain.transaction.input.CreateTransactionRequest;

public class TransactionConverter {

	public static Transaction convert(CreateTransactionRequest request) {

		return new Transaction() //
				.withAmount(Math.abs(request.getAmount())) //
				.withCurrencyCode(request.getCurrencyCode()) //
				.withDescription(request.getDescription()) //
				.withTransactionDate(request.getTransactionDate()) //
				.withCreatedAt(LocalDateTime.now()) //
				;
	}

	private TransactionConverter() {
		// Empty on purpose
	}
}
