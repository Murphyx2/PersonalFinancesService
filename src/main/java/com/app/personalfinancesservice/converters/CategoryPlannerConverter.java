package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.domain.categoryplanner.input.CreateCategoryPlannerRequest;

public class CategoryPlannerConverter {

	private static final String CATEGORY_PLANNER = "CATEGORY_PLANNER";

	public static CategoryPlanner convert(CreateCategoryPlannerRequest request) {

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", CATEGORY_PLANNER);

		UUID budgetUUID = UUIDConverter //
				.convert(request.getBudgetId(), "budgetId", CATEGORY_PLANNER);

		UUID categoryUUID = UUIDConverter //
				.convert(request.getCategoryId(), "categoryId", CATEGORY_PLANNER);

		return new CategoryPlanner() //
				.withUserId(userId) //
				.withBudgetId(budgetUUID) //
				.withPlannedAmount(Math.abs(request.getPlannedAmount())) //
				.withCreatedAt(LocalDateTime.now()).withCategory(new Category().withId(categoryUUID));
	}

	public static CategoryPlanner convert(CategoryPlannerDTO request, //
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
