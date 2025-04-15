package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.transaction.input.CreateTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetListTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.output.CreateTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetListTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;

public interface TransactionServiceBase {

	CreateTransactionResponse createTransaction(CreateTransactionRequest request);

	GetTransactionResponse getTransaction(GetTransactionRequest request);

	GetListTransactionResponse getListTransaction(GetListTransactionRequest request);

	GetTransactionTypeResponse getTransactionType();
}
