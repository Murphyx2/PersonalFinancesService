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

import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetListCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetListCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.UpdateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.service.CategoryPlannerServiceService;
import jakarta.validation.constraints.Null;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.BUDGET)
public class CategoryPlannerController {

	private final CategoryPlannerServiceService categoryPlannerService;

	public CategoryPlannerController(CategoryPlannerServiceService categoryPlannerService) {
		this.categoryPlannerService = categoryPlannerService;
	}

	@PostMapping(HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<CreateCategoryPlannerResponse> createCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@RequestBody CreateCategoryPlannerRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerService.createCategoryPlanner(request));
	}

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

	@GetMapping(HttpRoutes.CATEGORY_PLANNER + "/{id}")
	public ResponseEntity<GetCategoryPlannerResponse> getCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@PathVariable("id") String id) {

		GetCategoryPlannerRequest request = new GetCategoryPlannerRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerService.getCategoryPlanner(request));
	}

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

	@PutMapping(HttpRoutes.CATEGORY_PLANNER)
	public ResponseEntity<UpdateCategoryPlannerResponse> updateCategoryPlanner(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdateCategoryPlannerRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryPlannerService.updateCategoryPlanner(request));
	}

}
