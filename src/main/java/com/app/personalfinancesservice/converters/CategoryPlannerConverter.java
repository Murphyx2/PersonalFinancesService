package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;

public class CategoryPlannerConverter {

	private static final String CATEGORY_PLANNER = "CATEGORY_PLANNER";

	public static CategoryPlanner convert(CreateCategoryPlannerRequest request) {

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", CATEGORY_PLANNER);

		return new CategoryPlanner() //
				.withUserId(userId) //
				.withPlannedAmount(request.getPlannedAmount()) //
				.withCreatedAt(LocalDateTime.now());
	}

	private CategoryPlannerConverter() {
		// Empty on purpose
	}
}
