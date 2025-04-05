package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.budget.input.UpdateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetsResponse;
import com.app.personalfinancesservice.domain.budget.output.UpdateBudgetResponse;
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
	public ResponseEntity<GetBudgetsResponse> getBudget(@RequestHeader("X-User-id") String userId //
			, @PathVariable String id) {

		GetBudgetsRequest request = new GetBudgetsRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(budgetService.getBudgets(request));
	}

	// Fetch all budgets
	@GetMapping(path = "/")
	public ResponseEntity<GetBudgetsResponse> getBudgets(@RequestHeader("X-User-id") String userId) {

		GetBudgetsRequest request = new GetBudgetsRequest() //
				.withUserId(userId);

		return ResponseEntity.ok(budgetService.getBudgets(request));
	}

	@PutMapping
	public ResponseEntity<UpdateBudgetResponse> updateBudget(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdateBudgetRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(budgetService.updateBudget(request));
	}

}
