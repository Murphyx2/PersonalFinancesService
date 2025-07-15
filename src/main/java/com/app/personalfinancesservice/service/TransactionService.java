package com.app.personalfinancesservice.service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.TransactionConverter;
import com.app.personalfinancesservice.converters.TransactionDTOConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
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

		UUID categoryId = UUIDConverter //
				.convert(request.getCategoryId(), "categoryId", TRANSACTION_LABEL);

		// Convert request to entity
		Transaction transaction = TransactionConverter.convert(request) //
				.withCategory(new Category().withId(categoryId));

		return new CreateTransactionResponse() //
				.withTransaction(transactionRepositoryFacade.saveTransaction(transaction));
	}

	@Override
	public DeleteTransactionResponse deleteTransaction(DeleteTransactionRequest request) {

		transactionRepositoryFacade //
				.deleteTransaction(new TransactionDTO() //
						.withId(request.getId()) //
						.withUserId(request.getUserId()) //
				);
		return new DeleteTransactionResponse().withSuccess(true);
	}

	@Override
	public GetListTransactionResponse getListTransaction(GetListTransactionRequest request) {

		List<TransactionDTO> transactionList = transactionRepositoryFacade //
				.getTransactionsByBudgetIdAndUserId(request.getBudgetId(), request.getUserId());
		// Filter
		List<TransactionDTO> filteredTransaction = TransactionFilter //
				.filterByTransactionType(transactionList, request.getTransactionType());

		filteredTransaction = TransactionFilter //
				.filterByTransactionName(filteredTransaction, request.getCategoryName());

		// Sort
		List<TransactionDTO> sortedTransactions = TransactionSorter //
				.sortTransactions(filteredTransaction, request.getSortBy(), request.getSortDirection());

		return new GetListTransactionResponse() //
				.withTransactions(sortedTransactions);
	}

	@Override
	public GetTransactionResponse getTransaction(GetTransactionRequest request) {

		TransactionDTO transactionDTO = transactionRepositoryFacade //
				.getTransactionByIdAndUserId(request.getId(), request.getUserId());

		return new GetTransactionResponse() //
				.withTransaction(transactionDTO);
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

		TransactionDTO updatedTransactionDTO = transactionRepositoryFacade.updateTransaction(TransactionDTOConverter.convert(request));

		return new UpdateTransactionResponse().withTransaction(updatedTransactionDTO);
	}
}
