package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

	void deleteByIdAndUserId(UUID id, UUID userId);

	Optional<Transaction> getTransactionByIdAndUserId(UUID transactionId, UUID userId);

	List<Transaction> getTransactionsByBudgetIdAndUserId(UUID budgetId, UUID userId);
}
