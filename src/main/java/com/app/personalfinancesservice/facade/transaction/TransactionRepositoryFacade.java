package com.app.personalfinancesservice.facade.transaction;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.transaction.Transaction;

@Repository
public interface TransactionRepositoryFacade {

	void deleteTransaction(Transaction transaction);

	Transaction saveTransaction(Transaction transaction);

	Transaction getTransactionByIdAndUserId(String transactionId, String userId);

	List<Transaction> getTransactionsByBudgetIdAndUserId(String budgetId, String userId);
}
