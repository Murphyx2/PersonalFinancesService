package com.app.personalfinancesservice.facade.transaction;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.repository.TransactionRepository;
import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.facade.TransactionRepositoryFacade;

@Component
public class TransactionRepositoryFacadeImpl implements TransactionRepositoryFacade {

	private static final String TRANSACTION_LABEL = "TRANSACTION";
	private static final String USER_ID_LABEL = "userId";
	private static final String TRANSACTION_ID_LABEL = "transactionId";

	private final TransactionRepository transactionRepository;

	TransactionRepositoryFacadeImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction getTransactionByIdAndUserId(String transactionId, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, TRANSACTION_LABEL);

		UUID idUUID = UUIDConverter //
				.convert(transactionId, TRANSACTION_ID_LABEL, TRANSACTION_LABEL);

		return transactionRepository //
				.getTransactionByIdAndUserId(idUUID, userIdUUID) //
				.orElse(null);
	}

	@Override
	public List<Transaction> getTransactionsByBudgetIdAndUserId(String budgetId, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, TRANSACTION_LABEL);

		UUID budgetUUID = UUIDConverter //
				.convert(budgetId, TRANSACTION_ID_LABEL, TRANSACTION_LABEL);

		return transactionRepository //
				.getTransactionsByBudgetIdAndUserId(budgetUUID, userIdUUID);
	}

	@Override
	public void deleteTransaction(Transaction transaction) {

		transactionRepository.delete(transaction);
	}

	@Override
	public Transaction saveTransaction(Transaction transaction) {

		if (transaction == null) {
			throw new CreateNewItemException(TRANSACTION_LABEL, "transaction");
		}

		return transactionRepository.save(transaction);
	}
}
