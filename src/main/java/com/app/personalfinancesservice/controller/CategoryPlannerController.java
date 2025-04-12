package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.service.CategoryPlannerServiceService;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.BUDGET)
public class CategoryPlannerController {

	private final CategoryPlannerServiceService categoryPlannerServiceService;

	public CategoryPlannerController(CategoryPlannerServiceService categoryPlannerServiceService) {
		this.categoryPlannerServiceService = categoryPlannerServiceService;
	}

	@PostMapping("/{budgetId}" + HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<CreateCategoryPlannerResponse> createCategoryPlanner(@RequestHeader("X-User-id") String userId, //
 			@PathVariable String budgetId,
			@RequestBody CreateCategoryPlannerRequest request) {

		request.withUserId(userId) //
				.withBudgetId(budgetId);

		return ResponseEntity.ok(categoryPlannerServiceService.createCategoryPlanner(request));
	}

	@GetMapping("/{budgetId}" + HttpRoutes.CATEGORY_PLANNER + "/{id}")
	public ResponseEntity<GetCategoryPlannerResponse> getCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@PathVariable("budgetId") String budgetId, //
			@PathVariable("id") String id) {

		GetCategoryPlannerRequest request = new GetCategoryPlannerRequest() //
				.withId(id) //
				.withUserId(userId)
				.withBudgetId(budgetId);

		return ResponseEntity.ok(categoryPlannerServiceService.getCategoryPlanner(request));
	}

	@GetMapping("/{budgetId}" + HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<GetCategoryPlannerResponse> getCategoryPlanners(@RequestHeader("X-User-id") String userId, //
			@PathVariable("budgetId") String budgetId, //
			@RequestParam(required = false, defaultValue = "NAME") SortBy sortBy, //
			@RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection, //
			@RequestParam(required = false) TransactionType transactionType) {

		GetCategoryPlannerRequest request = new GetCategoryPlannerRequest() //
				.withUserId(userId) //
				.withSortBy(sortBy) //
				.withBudgetId(budgetId) //
				.withSortDirection(sortDirection) //
				.withTransactionType(transactionType);

		return ResponseEntity.ok(categoryPlannerServiceService.getCategoryPlanner(request));
	}



}
