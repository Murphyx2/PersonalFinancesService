package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.personalfinance.api.domain.budget.Budget;

@Component
public interface BudgetRepository extends JpaRepository<Budget, UUID> {

	boolean deleteBudgetByIdAndUserId(UUID id, UUID userId);

	boolean existsByIdAndUserId(UUID id, UUID userId);

	List<Budget> getAllByPortfolioIdAndUserId(UUID portfolioId, UUID userId);

	Optional<Budget> getBudgetByIdAndUserId(UUID id, UUID userId);
}
