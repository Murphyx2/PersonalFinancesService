package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;

@Repository
public interface CategoryPlannerRepository extends JpaRepository<CategoryPlanner, UUID> {
	boolean existsByCategory_IdAndBudgetId(UUID categoryId, UUID budgetId);

	CategoryPlanner getCategoryPlannerByIdAndUserId(UUID id, UUID userId);

	List<CategoryPlanner> getCategoryPlannerByUserIdAndBudget(UUID userId, Budget budget);
}
