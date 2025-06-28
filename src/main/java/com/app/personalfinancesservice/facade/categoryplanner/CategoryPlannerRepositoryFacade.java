package com.app.personalfinancesservice.facade.categoryplanner;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;

@Repository
public interface CategoryPlannerRepositoryFacade {

	CategoryPlanner saveCategoryPlanner(CategoryPlanner categoryPlanner);

	CategoryPlanner getCategoryPlanner(String id, String userId);

	List<CategoryPlanner> getCategoriesPlanner(UUID userId);



}
