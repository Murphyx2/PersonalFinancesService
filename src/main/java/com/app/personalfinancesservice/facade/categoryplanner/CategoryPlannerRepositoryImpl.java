package com.app.personalfinancesservice.facade.categoryplanner;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.repository.CategoryPlannerRepository;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;

@Component
public class CategoryPlannerRepositoryImpl implements CategoryPlannerRepositoryFacade {

	private static final String CATEGORY_PLANNER_LABEL = "CATEGORY_PLANNER";

	private final CategoryPlannerRepository categoryPlannerRepository;

	CategoryPlannerRepositoryImpl(CategoryPlannerRepository categoryPlannerRepository) {
		this.categoryPlannerRepository = categoryPlannerRepository;
	}

	@Override
	public CategoryPlanner getCategoryPlanner(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, "userId", CATEGORY_PLANNER_LABEL);

		UUID categoryId = UUIDConverter //
				.convert(id, "categoryId", CATEGORY_PLANNER_LABEL);

		return categoryPlannerRepository //
				.getCategoryPlannerByIdAndUserId(categoryId, userIdUUID);
	}

	@Override
	public CategoryPlanner saveCategoryPlanner(CategoryPlanner categoryPlanner) {
		return null;
	}
}
