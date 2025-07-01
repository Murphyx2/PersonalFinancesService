package com.app.personalfinancesservice.facade.budget;

import java.util.List;

import org.springframework.stereotype.Component;

import com.personalfinance.api.domain.budget.Budget;

@Component
public interface BudgetRepositoryFacade {

	boolean budgetExists(String id, String userId);

	boolean deleteBudget(String id, String userId);

	Budget getBudgetByIdAndUserId(String id, String userId);

	List<Budget> getBudgetsByPortfolioIdAndUserId(String portfolioId, String userId);

	Budget saveBudget(Budget budget);
}
