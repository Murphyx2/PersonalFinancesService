package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.personalfinancesservice.domain.budget.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {

	boolean existsByIdAndUserId(UUID id, UUID userId);

	List<Budget> getAllByUserIdAndPortfolioId(UUID userId, UUID portfolioId);

	List<Budget> getListByIdAndUserId(UUID id, UUID userId);

	Optional<Budget> getByIdAndUserId(UUID id, UUID userId);
}
