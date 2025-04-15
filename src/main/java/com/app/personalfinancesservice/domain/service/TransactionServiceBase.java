package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.transaction.input.CreateTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.DeleteTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetListTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.UpdateTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.output.CreateTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.DeleteTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetListTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;
import com.app.personalfinancesservice.domain.transaction.output.UpdateTransactionResponse;

public interface TransactionServiceBase {

	CreateTransactionResponse createTransaction(CreateTransactionRequest request);

	GetListTransactionResponse getListTransaction(GetListTransactionRequest request);

	GetTransactionResponse getTransaction(GetTransactionRequest request);

	GetTransactionTypeResponse getTransactionType();

	UpdateTransactionResponse updateTransaction(UpdateTransactionRequest request);

	DeleteTransactionResponse deleteTransaction(DeleteTransactionRequest request);
}
