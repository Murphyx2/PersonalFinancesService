package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.service.TransactionService;
import com.personalfinance.api.domain.transaction.TransactionType;
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
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(HttpRoutes.API_ROOT)
@Tag(name = "Transaction Management", description = "Endpoint to manage transactions, income, expenses or others")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Operation(summary = "Create a transaction", description = "Create a new transaction")
	@PostMapping(path = HttpRoutes.TRANSACTIONS)
	public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestHeader("X-User-id") String userId, //
			@Valid @RequestBody CreateTransactionRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(transactionService.createTransaction(request));
	}

	@Operation(summary = "Delete a transaction", description = "Delete a transaction by its id")
	@DeleteMapping(path = HttpRoutes.TRANSACTIONS + "/{id}")
	public ResponseEntity<DeleteTransactionResponse> deleteTransaction(@RequestHeader("X-User-id") String userId, //
			@PathVariable(value = "id") String id) {

		DeleteTransactionRequest request = new DeleteTransactionRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(transactionService.deleteTransaction(request));
	}

	@Operation(summary = "Get a transaction", description = "Get a transaction by its id")
	@GetMapping(path = HttpRoutes.TRANSACTIONS + "/{id}")
	public ResponseEntity<GetTransactionResponse> getTransaction(@RequestHeader("X-User-id") String userId, //
			@PathVariable(value = "id") String id) {

		GetTransactionRequest request = new GetTransactionRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(transactionService.getTransaction(request));
	}

	@Operation(summary = "Get a list of transaction types", description = "Get a list of transaction types")
	@GetMapping(path = HttpRoutes.TRANSACTIONS + "/type")
	public ResponseEntity<GetTransactionTypeResponse> getTransactionType() {

		return ResponseEntity.ok(transactionService.getTransactionType());
	}

	@Operation(summary = "Get a list of transactions", description = "Get a list of transactions by user id, budget id")
	@GetMapping(path = HttpRoutes.TRANSACTIONS)
	public ResponseEntity<GetListTransactionResponse> getTransactions(@RequestHeader("X-User-id") String userId, //
			@RequestParam String budgetId, @RequestParam(required = false) SortBy sortBy, //
			@RequestParam(required = false) SortDirection sortDirection, //
			@RequestParam(required = false) String categoryName, //
			@RequestParam(required = false) TransactionType transactionType) {

		GetListTransactionRequest request = new GetListTransactionRequest() //
				.withUserId(userId) //
				.withBudgetId(budgetId) //
				.withSortBy(sortBy) //
				.withSortDirection(sortDirection) //
				.withCategoryName(categoryName) //
				.withTransactionType(transactionType) //
				;

		return ResponseEntity.ok(transactionService.getListTransaction(request));
	}

	@Operation(summary = "Update a transaction", description = "Update a transaction")
	@PutMapping(path = HttpRoutes.TRANSACTIONS)
	public ResponseEntity<UpdateTransactionResponse> updateTransaction(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdateTransactionRequest request) {

		request.withUserId(userId);
		return ResponseEntity.ok(transactionService.updateTransaction(request));
	}

}
