package com.app.personalfinancesservice.service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.TransactionConverter;
import com.app.personalfinancesservice.converters.TransactionDTOConverter;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.TransactionFilter;
import com.app.personalfinancesservice.filter.TransactionSorter;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.transaction.Transaction;
import com.personalfinance.api.domain.transaction.TransactionType;
import com.personalfinance.api.domain.transaction.dto.TransactionDTO;
import com.personalfinance.api.domain.transaction.input.CreateTransactionRequest;
import com.personalfinance.api.domain.transaction.input.DeleteTransactionRequest;
import com.personalfinance.api.domain.transaction.input.GetListTransactionRequest;
import com.personalfinance.api.domain.transaction.input.GetTransactionRequest;
import com.personalfinance.api.domain.transaction.input.UpdateTransactionRequest;
import com.personalfinance.api.domain.transaction.output.CreateTransactionResponse;
import com.personalfinance.api.domain.transaction.output.DeleteTransactionResponse;
import com.personalfinance.api.domain.transaction.output.GetListTransactionResponse;
import com.personalfinance.api.domain.transaction.output.GetTransactionResponse;
import com.personalfinance.api.domain.transaction.output.GetTransactionTypeResponse;
import com.personalfinance.api.domain.transaction.output.UpdateTransactionResponse;
import com.personalfinance.api.facade.BudgetRepositoryFacade;
import com.personalfinance.api.facade.CategoryRepositoryFacade;
import com.personalfinance.api.facade.TransactionRepositoryFacade;
import com.personalfinance.api.service.TransactionServiceBase;

@Service
public class TransactionService implements TransactionServiceBase {

	private static final String TRANSACTION_LABEL = "TRANSACTION";

	TransactionRepositoryFacade transactionRepositoryFacade;
	BudgetRepositoryFacade budgetRepositoryFacade;
	CategoryRepositoryFacade categoryRepositoryFacade;

	public TransactionService(TransactionRepositoryFacade transactionRepositoryFacade, //
			BudgetRepositoryFacade budgetRepositoryFacade,  //
			CategoryRepositoryFacade categoryRepositoryFacade) {
		this.transactionRepositoryFacade = transactionRepositoryFacade;
		this.budgetRepositoryFacade = budgetRepositoryFacade;
		this.categoryRepositoryFacade = categoryRepositoryFacade;
	}

	@Override
	public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {

		// Check if Budget Exist
		if (!budgetRepositoryFacade.budgetExists(request.getBudgetId(), request.getUserId())) {
			String message = String.format("Budget with id %s does not exist", request.getBudgetId());
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}
		// Check if Category exists

		Category category = categoryRepositoryFacade //
				.getCategory(request.getCategoryId(), request.getUserId());
		if (category == null) {
			String message = String.format("Category with id %s does not exist", request.getCategoryId());
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		// Convert and save
		Transaction transaction = TransactionConverter.convert(request) //
				.withCategory(category);

		TransactionDTO updatedTransactionDTO = TransactionDTOConverter //
				.convert(transactionRepositoryFacade.saveTransaction(transaction));

		return new CreateTransactionResponse() //
				.withTransaction(updatedTransactionDTO);
	}

	@Override
	public DeleteTransactionResponse deleteTransaction(DeleteTransactionRequest request) {

		Transaction transaction = transactionRepositoryFacade //
				.getTransactionByIdAndUserId(request.getId(), request.getUserId());
		if (transaction == null) {
			return new DeleteTransactionResponse().withSuccess(true);
		}

		transactionRepositoryFacade.deleteTransaction(transaction);
		return new DeleteTransactionResponse().withSuccess(true);
	}

	@Override
	public GetListTransactionResponse getListTransaction(GetListTransactionRequest request) {

		List<Transaction> transactionList = transactionRepositoryFacade //
				.getTransactionsByBudgetIdAndUserId(request.getBudgetId(), request.getUserId());
		// Filter
		List<Transaction> filteredTransaction = TransactionFilter //
				.filterByTransactionType(transactionList, request.getTransactionType());

		filteredTransaction = TransactionFilter //
				.filterByTransactionName(filteredTransaction, request.getCategoryName());

		// Sort
		List<Transaction> sortedTransactions = TransactionSorter //
				.sortTransactions(filteredTransaction, request.getSortBy(), request.getSortDirection());

		return new GetListTransactionResponse() //
				.withTransactions(TransactionDTOConverter //
						.convertMany(sortedTransactions) //
				);
	}

	@Override
	public GetTransactionResponse getTransaction(GetTransactionRequest request) {

		Transaction transaction = transactionRepositoryFacade //
				.getTransactionByIdAndUserId(request.getId(), request.getUserId());

		return new GetTransactionResponse() //
				.withTransaction(TransactionDTOConverter.convert(transaction));
	}

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

	@Override
	public UpdateTransactionResponse updateTransaction(UpdateTransactionRequest request) {

		//Get transaction
		Transaction transaction = transactionRepositoryFacade //
				.getTransactionByIdAndUserId(request.getId(), request.getUserId());
		if (transaction == null) {
			String message = String.format("Transaction with id %s does not exist", request.getId());
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		// Get Category
		Category category = categoryRepositoryFacade //
				.getCategory(request.getCategoryId(), request.getUserId());
		if (category == null) {
			String message = String.format("Category with id %s does not exist", request.getCategoryId());
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		Transaction updatedTransaction = TransactionConverter //
				.convert(transaction, category, request);

		TransactionDTO updatedTransactionDTO = TransactionDTOConverter //
				.convert(transactionRepositoryFacade.saveTransaction(updatedTransaction) //
				);

		return new UpdateTransactionResponse().withTransaction(updatedTransactionDTO);
	}
}
