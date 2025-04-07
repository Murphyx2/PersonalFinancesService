package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;

public interface TransactionServiceBase {

	GetTransactionTypeResponse getTransactionType();
}
