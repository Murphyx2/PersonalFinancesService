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
import com.app.personalfinancesservice.service.BudgetService;
import com.personalfinance.api.domain.budget.input.CreateBudgetRequest;
import com.personalfinance.api.domain.budget.input.DeleteBudgetRequest;
import com.personalfinance.api.domain.budget.input.GetBudgetRequest;
import com.personalfinance.api.domain.budget.input.GetBudgetsRequest;
import com.personalfinance.api.domain.budget.input.UpdateBudgetRequest;
import com.personalfinance.api.domain.budget.output.CreateBudgetResponse;
import com.personalfinance.api.domain.budget.output.DeleteBudgetResponse;
import com.personalfinance.api.domain.budget.output.GetBudgetResponse;
import com.personalfinance.api.domain.budget.output.GetBudgetsResponse;
import com.personalfinance.api.domain.budget.output.UpdateBudgetResponse;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(HttpRoutes.API_ROOT)
@Tag(
		name = "Budget Management",
		description = "Endpoints for managing budgets"
)
public class BudgetController {

	private final BudgetService budgetService;

	private static final String BUDGET_ROUTE = HttpRoutes.BUDGET;

	BudgetController(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@Operation(summary = "Create a budget")
	@PostMapping(BUDGET_ROUTE)
	public ResponseEntity<CreateBudgetResponse> createBudget(@RequestHeader("X-User-id") String userId, //
			@RequestBody CreateBudgetRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(budgetService.createBudget(request));
	}

	@Operation(summary = "Deletes a budget")
	@DeleteMapping(path = BUDGET_ROUTE + "/{id}")
	public ResponseEntity<DeleteBudgetResponse> deleteBudget(@RequestHeader("X-User-id") String userId, //
			@PathVariable String id) {

		DeleteBudgetRequest request = new DeleteBudgetRequest().withId(id).withUserId(userId);

		return ResponseEntity.ok(budgetService.deleteBudget(request));
	}


	@Operation(summary = "Get a budget by ID", description = "Retrieves a budget by its id")
	@GetMapping(path = BUDGET_ROUTE + "/{id}")
	public ResponseEntity<GetBudgetResponse> getBudget(@RequestHeader("X-User-id") String userId, //
			@PathVariable String id) {

		GetBudgetRequest request = new GetBudgetRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(budgetService.getBudget(request));
	}

	// Fetch all budgets
	@Operation(summary = "Get a list budgets by portfolio ID", description = "Retrieves a list of budgets by portfolio id")
	@GetMapping(HttpRoutes.PORTFOLIO + "/{portfolioId}" + HttpRoutes.BUDGET)
	public ResponseEntity<GetBudgetsResponse> getBudgets(@RequestHeader("X-User-id") String userId, //
			@PathVariable String portfolioId,
			@RequestParam(required = false, defaultValue = "NAME") SortBy sortBy, //
			@RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection) {

		GetBudgetsRequest request = new GetBudgetsRequest() //
				.withUserId(userId) //
				.withSortBy(sortBy) //
				.withPortfolioId(portfolioId)
				.withSortDirection(sortDirection);

		return ResponseEntity.ok(budgetService.getBudgets(request));
	}

	@Operation(summary = "Update a budget", description = "Update a budget")
	@PutMapping(BUDGET_ROUTE)
	public ResponseEntity<UpdateBudgetResponse> updateBudget(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdateBudgetRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(budgetService.updateBudget(request));
	}

}
