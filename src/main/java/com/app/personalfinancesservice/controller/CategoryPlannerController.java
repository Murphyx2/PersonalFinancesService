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
import com.app.personalfinancesservice.service.CategoryPlannerService;
import com.personalfinance.api.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.GetListCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.GetListCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.UpdateCategoryPlannerResponse;
import com.personalfinance.api.domain.transaction.TransactionType;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;
import jakarta.validation.constraints.Null;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.BUDGET)
@Tag(
		name = "Category Planner Management",
		description = "Endpoint to manage categories target amount"
)
public class CategoryPlannerController {

	private final CategoryPlannerService categoryPlannerService;

	public CategoryPlannerController(CategoryPlannerService categoryPlannerService) {
		this.categoryPlannerService = categoryPlannerService;
	}

	@Operation(summary = "Create a Planner Category", description = "Create a planner for a category")
	@PostMapping(HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<CreateCategoryPlannerResponse> createCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@RequestBody CreateCategoryPlannerRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerService.createCategoryPlanner(request));
	}

	@Operation(summary = "Delete a category planner", description = "Delete a category planner by its id")
	@DeleteMapping(HttpRoutes.CATEGORY_PLANNER + "/{id}")
	public ResponseEntity<Null> deleteCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@PathVariable("id") String id) {

		DeleteCategoryPlannerRequest request = new DeleteCategoryPlannerRequest() //
				.withId(id)  //
				.withUserId(userId) //
				;

		categoryPlannerService.deleteCategoryPlanner(request);

		return ResponseEntity.ok(null);
	}

	@Operation(summary = "Get a category planner", description = "Get a category planner by its id")
	@GetMapping(HttpRoutes.CATEGORY_PLANNER + "/{id}")
	public ResponseEntity<GetCategoryPlannerResponse> getCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@PathVariable("id") String id) {

		GetCategoryPlannerRequest request = new GetCategoryPlannerRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerService.getCategoryPlanner(request));
	}

	@Operation(summary = "Get a list of categories planner", description = "Get a list of categories planner by a budget id")
	@GetMapping(HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<GetListCategoryPlannerResponse> getCategoryPlanners(@RequestHeader("X-User-id") String userId, //
			@RequestParam String budgetId, //
			@RequestParam(required = false, defaultValue = "NAME") SortBy sortBy, //
			@RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection, //
			@RequestParam(required = false) String categoryName, //
			@RequestParam(required = false) TransactionType transactionType) {

		GetListCategoryPlannerRequest request = new GetListCategoryPlannerRequest() //
				.withUserId(userId) //
				.withSortBy(sortBy) //
				.withBudgetId(budgetId) //
				.withSortDirection(sortDirection) //
				.withCategoryName(categoryName) //
				.withTransactionType(transactionType);

		return ResponseEntity.ok(categoryPlannerService.getListCategoryPlanner(request));
	}

	@Operation(summary = "Update a category planner", description = "Update a category planner")
	@PutMapping(HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<UpdateCategoryPlannerResponse> updateCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdateCategoryPlannerRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerService.updateCategoryPlanner(request));
	}

}
