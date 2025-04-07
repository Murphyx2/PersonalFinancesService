package com.app.personalfinancesservice.service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.domain.service.TransactionServiceBase;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;

@Service
public class TransactionService implements TransactionServiceBase {

	@Override
	public GetTransactionTypeResponse getTransactionType() {

		// Sort them to always keep the same older.
		LinkedHashSet<TransactionType> transactionType = TransactionType //
				.getTransactionTypes().stream() //
				.sorted(Comparator.comparing(TransactionType::name)) //
				.collect(Collectors.toCollection(LinkedHashSet::new));

		return new GetTransactionTypeResponse() //
				.withTransactionType(transactionType);
	}
}
