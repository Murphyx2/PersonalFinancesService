package com.app.personalfinancesservice.converters;

import java.util.ArrayList;
import java.util.List;

import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.domain.budget.dto.BudgetDTO;

public class BudgetDTOConverter {

	public static BudgetDTO convert(Budget budget) {

		if (budget == null) {
			return null;
		}

		return new BudgetDTO().withId(budget.getId().toString()) //
				.withUserId(budget.getUserId().toString()) //
				.withPortfolioId(budget.getPortfolioId().toString()) //
				.withName(budget.getName()) //
				.withDescription(budget.getDescription()) //
				.withTransactions(TransactionDTOConverter //
						.convertMany(budget.getTransactions()) //
				) //
				.withCategoryPlanners(CategoryPlannerDTOConverter //
						.convertMany(budget.getCategoryPlanners()) //
				) //
				.withStartAt(budget.getStartAt()) //
				.withEndAt(budget.getEndAt()) //
				.withCreatedAt(budget.getCreatedAt()) //
				.withUpdatedAt(budget.getUpdatedAt());
	}

	public static List<BudgetDTO> convertMany(List<Budget> budgets) {

		if(budgets == null || budgets.isEmpty()){
			return new ArrayList<>();
		}

		return budgets //
				.stream() //
				.map(BudgetDTOConverter::convert) //
				.toList();
	}

	private BudgetDTOConverter() {
		// Empty on purpose
	}
}
