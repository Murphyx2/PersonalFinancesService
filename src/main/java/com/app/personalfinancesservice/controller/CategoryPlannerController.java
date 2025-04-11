package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.service.CategoryPlannerServiceService;

@RestController
@RequestMapping(HttpRoutes.CATEGORY_PLANNER)
public class CategoryPlannerController {

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
}
