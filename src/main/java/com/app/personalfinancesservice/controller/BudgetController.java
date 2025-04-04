package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetResponse;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.service.BudgetService;

@RestController
@RequestMapping(HttpRoutes.BUDGET)
public class BudgetController {

	BudgetService budgetService;

	BudgetController(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@PostMapping
	public ResponseEntity<CreateBudgetResponse> createBudget(@RequestHeader("X-User-id") String userId, //
			@RequestBody CreateBudgetRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(budgetService.createBudget(request));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<GetBudgetResponse> getBudget(@RequestHeader("X-User-id") String userId //
			, @PathVariable String id) {

		GetBudgetRequest request = new GetBudgetRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(budgetService.getBudget(request));
	}

	// Fetch all budgets
	@GetMapping(path = "/")
	public ResponseEntity<GetBudgetResponse> getBudget(@RequestHeader("X-User-id") String userId) {

		GetBudgetRequest request = new GetBudgetRequest() //
				.withUserId(userId);

		return ResponseEntity.ok(budgetService.getBudget(request));
	}

}
