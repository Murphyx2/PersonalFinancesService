package com.app.personalfinancesservice.facade.categoryplanner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.repository.CategoryPlannerJPARepository;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.facade.CategoryPlannerRepositoryFacade;

@Component
public class CategoryPlannerRepositoryImpl implements CategoryPlannerRepositoryFacade {

	private static final String CATEGORY_PLANNER_LABEL = "CATEGORY_PLANNER";

	private static final String USER_ID_LABEL = "userId";

	private final CategoryPlannerJPARepository categoryPlannerJPARepository;

	public CategoryPlannerRepositoryImpl( //
			CategoryPlannerJPARepository categoryPlannerJPARepository //
	) {
		this.categoryPlannerJPARepository = categoryPlannerJPARepository;
	}

	@Override
	public boolean categoryPlannerExists(String categoryId, String budgetId, String userId) {

		UUID categoryUUID = UUIDConverter //
				.convert(categoryId, "categoryId", CATEGORY_PLANNER_LABEL);

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID budgetUUID = UUIDConverter //
				.convert(budgetId, "budgetId", CATEGORY_PLANNER_LABEL);

		List<CategoryPlanner> categoryPlanners = categoryPlannerJPARepository //
				.getCategoryPlannersByUserIdAndBudgetId(userIdUUID, budgetUUID);

		return categoryPlanners //
				.stream() //
				.anyMatch(categoryPlanner -> //
						categoryPlanner.getCategory().getId().equals(categoryUUID) //
				);
	}

	@Override
	public boolean categoryPlannerExists(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID categoryPlannerId = UUIDConverter //
				.convert(id, "categoryPlannerId", CATEGORY_PLANNER_LABEL);

		Optional<CategoryPlanner> categoryPlanner = categoryPlannerJPARepository //
				.getCategoryPlannerByIdAndUserId(categoryPlannerId, userIdUUID);

		return categoryPlanner.isPresent();
	}

	@Override
	public void deleteCategoryPlanner(CategoryPlanner categoryPlanner) {
		categoryPlannerJPARepository.delete(categoryPlanner);
	}

	@Override
	public List<CategoryPlanner> getCategoriesPlanner(String userId, String budgetId) {
		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID budgetUUID = UUIDConverter //
				.convert(budgetId, "budgetId", CATEGORY_PLANNER_LABEL);

		return categoryPlannerJPARepository //
				.getCategoryPlannersByUserIdAndBudgetId(userUUID, budgetUUID);
	}

	@Override
	public CategoryPlanner getCategoryPlanner(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID categoryPlannerId = UUIDConverter //
				.convert(id, "categoryPlannerId", CATEGORY_PLANNER_LABEL);

		return categoryPlannerJPARepository //
				.getCategoryPlannerByIdAndUserId(categoryPlannerId, userIdUUID) //
				.orElse(null);
	}

	@Override
	public CategoryPlanner saveCategoryPlanner(CategoryPlanner categoryPlanner) {

		if (categoryPlanner == null) {
			throw new MissingIdException(CATEGORY_PLANNER_LABEL, "categoryPlanner");
		}

		return categoryPlannerJPARepository.save(categoryPlanner);
	}
}
