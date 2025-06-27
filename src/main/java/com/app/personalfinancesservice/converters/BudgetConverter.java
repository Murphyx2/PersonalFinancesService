package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;

import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.domain.budget.input.CreateBudgetRequest;
import com.personalfinance.api.domain.budget.input.UpdateBudgetRequest;

public class BudgetConverter {

	private static final String BUDGET_LABEL = "BUDGET";

	public static Budget convert(CreateBudgetRequest request) {
		return new Budget() //
				.withUserId(UUIDConverter.convert(request.getUserId(), "userId", BUDGET_LABEL)) //
				.withPortfolioId(UUIDConverter.convert(request.getPortfolioId(), "portfolioId", BUDGET_LABEL)) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withStartAt(request.getStartAt()) //
				.withEndAt(request.getEndAt()) //
				.withCreatedAt(LocalDateTime.now()) //
				;
	}

	public static Budget convert(UpdateBudgetRequest request, Budget oldBudget) {

		if (oldBudget == null) {
			return null;
		}

		return new Budget() //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withStartAt(request.getStartAt()) //
				.withEndAt(request.getEndAt()) //
				.withId(oldBudget.getId()) //
				.withUserId(oldBudget.getUserId()) //
				.withPortfolioId(oldBudget.getPortfolioId()) //
				.withTransactions(oldBudget.getTransactions()) //
				.withCreatedAt(oldBudget.getCreatedAt()) //
				.withUpdatedAt(LocalDateTime.now()) //
				;

	}

	private BudgetConverter() {
		// Empty on purpose
	}
}
