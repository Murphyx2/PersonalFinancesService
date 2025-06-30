package com.app.personalfinancesservice.facade.categoryplanner;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;

@Repository
public interface CategoryPlannerRepositoryFacade {

	boolean categoryPlannerExists(String categoryId, String budgetId, String userId);

	boolean categoryPlannerExists(String id, String userId);

	CategoryPlanner saveCategoryPlanner(CategoryPlanner categoryPlanner);

	CategoryPlanner getCategoryPlanner(String id, String userId);

	List<CategoryPlanner> getCategoriesPlanner(String budgetId, String userId);

	void deleteCategoryPlanner(CategoryPlanner categoryPlanner);

}
