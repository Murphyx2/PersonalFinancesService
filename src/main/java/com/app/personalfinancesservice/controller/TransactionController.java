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

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
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
import com.app.personalfinancesservice.service.TransactionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(HttpRoutes.API_ROOT)
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(path = HttpRoutes.TRANSACTIONS)
	public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestHeader("X-User-id") String userId, //
			@Valid @RequestBody CreateTransactionRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(transactionService.createTransaction(request));
	}

	@DeleteMapping(path = HttpRoutes.TRANSACTIONS + "/{id}")
	public ResponseEntity<DeleteTransactionResponse> deleteTransaction(@RequestHeader("X-User-id") String userId, //
			@PathVariable(value = "id") String id) {

		DeleteTransactionRequest request = new DeleteTransactionRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(transactionService.deleteTransaction(request));
	}

	@GetMapping(path = HttpRoutes.TRANSACTIONS)
	public ResponseEntity<GetListTransactionResponse> getListTransaction(@RequestHeader("X-User-id") String userId, //
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

	@GetMapping(path = HttpRoutes.TRANSACTIONS + "/{id}")
	public ResponseEntity<GetTransactionResponse> getTransactionType(@RequestHeader("X-User-id") String userId, //
			@PathVariable(value = "id") String id) {

		GetTransactionRequest request = new GetTransactionRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(transactionService.getTransaction(request));
	}

	@GetMapping(path = HttpRoutes.TRANSACTIONS + "/type")
	public ResponseEntity<GetTransactionTypeResponse> getTransactionType() {

		return ResponseEntity.ok(transactionService.getTransactionType());
	}

	@PutMapping(path = HttpRoutes.TRANSACTIONS)
	public ResponseEntity<UpdateTransactionResponse> updateTransaction(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdateTransactionRequest request) {

		request.withUserId(userId);
		return ResponseEntity.ok(transactionService.updateTransaction(request));
	}

}
