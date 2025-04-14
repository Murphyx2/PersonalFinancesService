package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.input.CreateTransactionRequest;
import com.app.personalfinancesservice.domain.transaction.output.CreateTransactionResponse;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;
import com.app.personalfinancesservice.service.TransactionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(HttpRoutes.API_ROOT)
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(path =HttpRoutes.TRANSACTIONS)
	public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestHeader("X-User-id") String userId, //
			@Valid @RequestBody CreateTransactionRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(transactionService.createTransaction(request));
	}

	@GetMapping(path = HttpRoutes.TRANSACTIONS + "/type")
	public ResponseEntity<GetTransactionTypeResponse> getTransactionType() {

		return ResponseEntity.ok(transactionService.getTransactionType());
	}
}
