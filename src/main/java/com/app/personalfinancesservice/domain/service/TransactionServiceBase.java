package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.transaction.input.CreateTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.output.CreateTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;

public interface TransactionServiceBase {

	CreateTransactionResponse createTransaction(CreateTransactionRequest request);

	GetTransactionResponse getTransaction(GetTransactionRequest request);



	GetTransactionTypeResponse getTransactionType();
}
