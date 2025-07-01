package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;

@Repository
public interface CategoryPlannerRepository extends JpaRepository<CategoryPlanner, UUID> {

	Optional<CategoryPlanner> getCategoryPlannerByIdAndUserId(UUID id, UUID userId);

	List<CategoryPlanner> getCategoryPlannersByUserIdAndBudgetId(UUID userId, UUID budgetId);
}
