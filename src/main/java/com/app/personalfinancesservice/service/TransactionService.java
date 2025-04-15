package com.app.personalfinancesservice.service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.TransactionConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.service.TransactionServiceBase;
import com.app.personalfinancesservice.domain.transaction.Transaction;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.domain.transaction.input.CreateTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetListTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.input.GetTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.output.CreateTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetListTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.TransactionFilter;
import com.app.personalfinancesservice.filter.TransactionSorter;
import com.app.personalfinancesservice.repository.TransactionRepository;

@Service
public class TransactionService implements TransactionServiceBase {

	private static final String TRANSACTION_LABEL = "TRANSACTION";
	private static final String USER_ID_LABEL = "userId";

	TransactionRepository repository;

	BudgetService budgetService;
	CategoryService categoryService;

	public TransactionService(TransactionRepository repository, BudgetService budgetService, CategoryService categoryService) {
		this.repository = repository;
		this.budgetService = budgetService;
		this.categoryService = categoryService;
	}

	@Override
	public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {

		UUID budgetId = UUIDConverter.convert(request.getBudgetId(), "budgetId", TRANSACTION_LABEL);
		UUID categoryId = UUIDConverter.convert(request.getCategoryId(), "categoryId", TRANSACTION_LABEL);
		UUID userId = UUIDConverter.convert(request.getUserId(), USER_ID_LABEL, TRANSACTION_LABEL);

		//Check if Budget Exist
		if(!budgetService.budgetExists(budgetId,userId)) {
			String message = String.format("Budget with id %s does not exist", budgetId);
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}
		//Check if Category exists
		GetCategoryRequest categoryRequest = new GetCategoryRequest() //
				.withId(categoryId.toString()) //
				.withUserId(userId.toString());
		List<Category> category = categoryService.getCategory(categoryRequest).getCategory();
		if(category == null || category.isEmpty()) {
			String message = String.format("Category with id %s does not exist", categoryId);
			throw new NotFoundException(TRANSACTION_LABEL, message);
		}

		// Convert and save
		Transaction transaction = TransactionConverter.convert(request) //
				.withBudgetId(budgetId) //
				.withUserId(userId) //
				.withCategory(category.getFirst());


		return new CreateTransactionResponse().withTransaction(repository.save(transaction));
	}

	@Override
	public GetTransactionResponse getTransaction(GetTransactionRequest request) {

		UUID userId = UUIDConverter.convert(request.getUserId(), USER_ID_LABEL, TRANSACTION_LABEL);
		UUID transactionId = UUIDConverter.convert(request.getId(), "transactionId", TRANSACTION_LABEL);

		Transaction transaction = repository.getTransactionByIdAndUserId(transactionId, userId);

		return new GetTransactionResponse().withTransaction(transaction);
	}

	@Override
	public GetListTransactionResponse getListTransaction(GetListTransactionRequest request) {

		UUID userId = UUIDConverter.convert(request.getUserId(), USER_ID_LABEL, TRANSACTION_LABEL);
		UUID budgetId = UUIDConverter.convert(request.getBudgetId(), "budgetId", TRANSACTION_LABEL);

		List<Transaction> transactionList = repository.getTransactionsByBudgetIdAndUserId(budgetId, userId);
		// Filter
		List<Transaction> filteredTransaction = TransactionFilter //
				.filterByTransactionType(transactionList, request.getTransactionType());

		filteredTransaction = TransactionFilter //
				.filterByTransactionName(filteredTransaction, request.getCategoryName());

		// Sort
		List<Transaction> sortedTransactions = TransactionSorter //
				.sortTransactions(filteredTransaction, request.getSortBy(), request.getSortDirection());

		return new GetListTransactionResponse().withTransactions(sortedTransactions);
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
}
