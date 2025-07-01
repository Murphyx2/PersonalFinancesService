package com.app.personalfinancesservice.facade.transaction;

import java.util.List;

import org.springframework.stereotype.Component;

import com.personalfinance.api.domain.transaction.Transaction;

@Component
public interface TransactionRepositoryFacade {

	void deleteTransaction(Transaction transaction);

	Transaction getTransactionByIdAndUserId(String transactionId, String userId);

	List<Transaction> getTransactionsByBudgetIdAndUserId(String budgetId, String userId);

	Transaction saveTransaction(Transaction transaction);
}
