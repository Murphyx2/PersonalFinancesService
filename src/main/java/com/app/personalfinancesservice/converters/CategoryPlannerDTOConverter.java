package com.app.personalfinancesservice.converters;

import java.util.ArrayList;
import java.util.List;

import com.personalfinance.api.domain.category.dto.CategoryDTO;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.domain.categoryplanner.input.UpdateCategoryPlannerRequest;

public class CategoryPlannerDTOConverter {

	public static CategoryPlannerDTO convert(CategoryPlanner categoryPlanner) {

		if (categoryPlanner == null) {
			return null;
		}

		return new CategoryPlannerDTO() //
				.withId(categoryPlanner.getId().toString()) //
				.withUserId(categoryPlanner.getUserId().toString()) //
				.withBudgetId(categoryPlanner.getBudgetId().toString()) //
				.withCategory(CategoryDTOConverter.convert(categoryPlanner.getCategory())) //
				.withPlannedAmount(categoryPlanner.getPlannedAmount()) //
				.withCreatedAt(categoryPlanner.getCreatedAt()) //
				.withUpdatedAt(categoryPlanner.getUpdatedAt()) //
				;
	}

	public static CategoryPlannerDTO convert(UpdateCategoryPlannerRequest request) {

		if (request == null) {
			return null;
		}

		return new CategoryPlannerDTO() //
				.withId(request.getId()) //
				.withUserId(request.getUserId()) //
				.withCategory(new CategoryDTO() //
						.withId(request.getCategoryId())) //
				.withPlannedAmount(request.getPlannedAmount()) //
				;
	}

	public static List<CategoryPlannerDTO> convertMany(List<CategoryPlanner> categoryPlanners) {

		if (categoryPlanners == null || categoryPlanners.isEmpty()) {
			return new ArrayList<>();
		}

		return categoryPlanners //
				.stream() //
				.map(CategoryPlannerDTOConverter::convert) //
				.toList();
	}

	private CategoryPlannerDTOConverter() {
		// Empty on purpose
	}
}
