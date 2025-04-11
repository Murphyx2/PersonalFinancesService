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
@RequestMapping(HttpRoutes.CATEGORY_PLANNER)
public class CategoryPlannerController {

	private static String CATEGORY_PLANNER_ROUTE = "/category_planner";

	private final CategoryPlannerServiceService categoryPlannerServiceService;

	public CategoryPlannerController(CategoryPlannerServiceService categoryPlannerServiceService) {
		this.categoryPlannerServiceService = categoryPlannerServiceService;
	}

	@PostMapping
	public ResponseEntity<CreateCategoryPlannerResponse> createCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@RequestBody CreateCategoryPlannerRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerServiceService.createCategoryPlanner(request));
	}

	@GetMapping("/{budgetId}/{id}")
	public ResponseEntity<GetCategoryPlannerResponse> getCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@PathVariable("id") String id, @PathVariable("budgetId") String budgetId) {

		GetCategoryPlannerRequest request = new GetCategoryPlannerRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerServiceService.getCategory(request));
	}

	@GetMapping
	public ResponseEntity<GetCategoryPlannerResponse> getCategoryPlanners(@RequestHeader("X-User-id") String userId, //
			@RequestParam(required = false, defaultValue = "NAME") SortBy sortBy, //
			@RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection, //
			@RequestParam(required = false) TransactionType transactionType) {

		GetCategoryPlannerRequest request = new GetCategoryPlannerRequest() //
				.withUserId(userId) //
				.withSortBy(sortBy) //
				.withSortDirection(sortDirection) //
				.withTransactionType(transactionType);

		return ResponseEntity.ok(categoryPlannerServiceService.getCategory(request));
	}



}
