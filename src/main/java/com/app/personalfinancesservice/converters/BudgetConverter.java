package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;

import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;

public class BudgetConverter {

	private static final String BUDGET_LABEL = "BUDGET";

	public static Budget convert(CreateBudgetRequest request) {
		return new Budget()
				.withUserId(UUIDConverter.convert(request.getUserId(), "userId", BUDGET_LABEL)) //
				.withPortfolioId(UUIDConverter.convert(request.getPortfolioId(), "portfolioId", BUDGET_LABEL)) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withStartAt(request.getStartDate()) //
				.withEndAt(request.getEndDate()) //
				.withCreatedAt(LocalDateTime.now()) //
				;
	}

	private BudgetConverter(){
		// Empty on purpose
	}
}
