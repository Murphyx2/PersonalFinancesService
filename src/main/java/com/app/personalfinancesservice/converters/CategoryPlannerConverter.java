package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;

public class CategoryPlannerConverter {

	private static final String CATEGORY_PLANNER = "CATEGORY_PLANNER";

	public static CategoryPlanner convert(CreateCategoryPlannerRequest request) {

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", CATEGORY_PLANNER);

		return new CategoryPlanner() //
				.withUserId(userId) //
				.withPlannedAmount(Math.abs(request.getPlannedAmount())) //
				.withCreatedAt(LocalDateTime.now());
	}

	public static CategoryPlanner convert(UpdateCategoryPlannerRequest request, //
			Category category, //
			CategoryPlanner oldCategoryPlanner) {

		return oldCategoryPlanner //
				.withCategory(category) //
				.withPlannedAmount(Math.abs(request.getPlannedAmount())) //
				.withUpdatedAt(LocalDateTime.now());
	}

	private CategoryPlannerConverter() {
		// Empty on purpose
	}
}
