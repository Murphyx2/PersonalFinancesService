package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.output.GetTransactionTypeResponse;
import com.app.personalfinancesservice.service.TransactionService;

@RestController
@RequestMapping(HttpRoutes.TRANSACTIONS)
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping(path = "/type")
	public ResponseEntity<GetTransactionTypeResponse> getTransactionType() {

		return ResponseEntity.ok(transactionService.getTransactionType());
	}
}
