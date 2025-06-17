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

import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.DeleteCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoriesRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.UpdateCategoryRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.DeleteCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoriesResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.UpdateCategoryResponse;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import com.app.personalfinancesservice.service.CategoryService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.CATEGORY)
@Tag(
		name = "Category Management",
		description = "Endpoint to manage Categories"
)
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "Create a Category", description = "Create a new category")
	@PostMapping
	public ResponseEntity<CreateCategoryResponse> createCategory(@RequestHeader("X-User-id") String userId, //
			@Valid @RequestBody CreateCategoryRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryService.createCategory(request));
	}

	@Operation(summary = "Delete a Category", description = "Delete a category by its Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteCategoryResponse> deleteCategory(@RequestHeader("X-User-id") String userId, //
			@PathVariable String id) {

		DeleteCategoryRequest request = new DeleteCategoryRequest() //
				.withUserId(userId) //
				.withId(id);

		return ResponseEntity.ok(categoryService.deleteCategory(request));
	}

	@Operation(summary = "Get a list of categories", description = "Get a list of categories by user id")
	@GetMapping()
	public ResponseEntity<GetCategoriesResponse> getCategories(@RequestHeader("X-User-id") String userId, //
			@RequestParam(required = false, defaultValue = "NAME") SortBy sortBy, //
			@RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection, //
			@RequestParam(required = false) TransactionType transactionType) {

		GetCategoriesRequest request = new GetCategoriesRequest() //
				.withUserId(userId) //
				.withSortBy(sortBy) //
				.withSortDirection(sortDirection) //
				.withTransactionType(transactionType);
		return ResponseEntity.ok(categoryService.getCategories(request));
	}

	@Operation(summary = "Get a category", description = "Get a category by user id")
	@GetMapping("/{id}")
	public ResponseEntity<GetCategoryResponse> getCategory(@RequestHeader("X-User-id") String userId, //
			@PathVariable String id) {

		GetCategoryRequest request = new GetCategoryRequest() //
				.withId(id) //
				.withUserId(userId);
		return ResponseEntity.ok(categoryService.getCategory(request));
	}

	@Operation(summary = "Update a category", description = "Update a category")
	@PutMapping()
	public ResponseEntity<UpdateCategoryResponse> updateCategory(@RequestHeader("X-User-id") String userId, //
			@Valid @RequestBody UpdateCategoryRequest request) {

		request.withUserId(userId);

		return ResponseEntity.ok(categoryService.updateCategory(request));
	}
}
