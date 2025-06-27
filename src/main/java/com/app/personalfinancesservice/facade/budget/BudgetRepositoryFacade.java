package com.app.personalfinancesservice.facade.budget;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.budget.Budget;

@Repository
public interface BudgetRepositoryFacade {

	Budget saveBudget(Budget budget);

	Budget getBudgetByIdAndUserId(String id, String userId);

	boolean deleteBudget(String id, String userId);

	boolean budgetExists(String id, String userId);

	List<Budget> getBudgetsByPortfolioIdAndUserId(String portfolioId, String userId);
}
