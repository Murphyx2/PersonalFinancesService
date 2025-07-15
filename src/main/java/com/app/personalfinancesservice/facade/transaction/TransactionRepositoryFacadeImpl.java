package com.app.personalfinancesservice.facade.transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.TransactionConverter;
import com.app.personalfinancesservice.converters.TransactionDTOConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.repository.CategoryRepository;
import com.app.personalfinancesservice.repository.TransactionRepository;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.domain.transaction.dto.TransactionDTO;
import com.personalfinance.api.facade.TransactionRepositoryFacade;

@Component
public class TransactionRepositoryFacadeImpl implements TransactionRepositoryFacade {

	private static final String TRANSACTION_LABEL = "TRANSACTION";
	private static final String USER_ID_LABEL = "userId";
	private static final String TRANSACTION_ID_LABEL = "transactionId";

	private final TransactionRepository transactionRepository;
	private final CategoryRepository categoryRepository;

	TransactionRepositoryFacadeImpl(TransactionRepository transactionRepository
	, CategoryRepository categoryRepository) {
		this.transactionRepository = transactionRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void deleteTransaction(TransactionDTO transactionDTO) {

		UUID userUUID = UUIDConverter //
				.convert(transactionDTO.getUserId(), USER_ID_LABEL, TRANSACTION_LABEL);

		UUID id = UUIDConverter //
				.convert(transactionDTO.getId(), TRANSACTION_ID_LABEL, TRANSACTION_LABEL);

		Optional<Transaction> transaction = transactionRepository //
				.getTransactionByIdAndUserId(id, userUUID);

		transaction.ifPresent(transactionRepository::delete);
	}

	@Override
	public TransactionDTO getTransactionByIdAndUserId(String transactionId, String userId) {

		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, TRANSACTION_LABEL);

		UUID id = UUIDConverter //
				.convert(transactionId, TRANSACTION_ID_LABEL, TRANSACTION_LABEL);

		return TransactionDTOConverter.convert(transactionRepository //
				.getTransactionByIdAndUserId(id, userUUID) //
				.orElse(null) //
		);
	}

	@Override
	public List<TransactionDTO> getTransactionsByBudgetIdAndUserId(String budgetId, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, TRANSACTION_LABEL);

		UUID budgetUUID = UUIDConverter //
				.convert(budgetId, TRANSACTION_ID_LABEL, TRANSACTION_LABEL);

		return TransactionDTOConverter.convertMany(transactionRepository //
				.getTransactionsByBudgetIdAndUserId(budgetUUID, userIdUUID) //
		);
	}

	@Override
	public TransactionDTO saveTransaction(Transaction transaction) {

		if (transaction == null) {
			throw new CreateNewItemException(TRANSACTION_LABEL, "transaction");
		}

		// Check if Category exists
		Optional<Category> category = categoryRepository //
				.getCategoryByIdAndUserId(transaction.getCategory().getId() //
						, transaction.getUserId() //
				);
		if (category.isEmpty()) {
			String message = String.format("Category with id %s does not exist" //
					, transaction.getCategory().getId());
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		transaction.withCategory(category.get());

		return TransactionDTOConverter.convert(transactionRepository.save(transaction));
	}

	@Override
	public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {

		UUID userUUID = UUIDConverter //
				.convert(transactionDTO.getUserId(), USER_ID_LABEL, TRANSACTION_LABEL);

		UUID id = UUIDConverter //
				.convert(transactionDTO.getId(), TRANSACTION_ID_LABEL, TRANSACTION_LABEL);

		//Get transaction
		Optional<Transaction> transaction = transactionRepository //
				.getTransactionByIdAndUserId(id, userUUID);

		if (transaction.isEmpty()) {
			String message = String.format("Transaction with id %s does not exist", transactionDTO.getId());
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		UUID categoryId = UUIDConverter //
				.convert(transactionDTO.getCategory().getId(), "categoryId", TRANSACTION_LABEL);

		// Get Category
		Optional<Category> category = categoryRepository //
				.getCategoryByIdAndUserId(categoryId, userUUID);
		if (category.isEmpty()) {
			String message = String.format("Category with id %s does not exist", categoryId);
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		Transaction transactionToUpdate = TransactionConverter //
				.convert(transaction.get(), transactionDTO)
				.withCategory(category.get());

		return TransactionDTOConverter //
				.convert(transactionRepository.save(transactionToUpdate));
	}
}
