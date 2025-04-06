package com.app.personalfinancesservice.domain.budget.output;

import java.util.List;

import com.app.personalfinancesservice.domain.budget.Budget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBudgetsResponse {

	private List<Budget> budgets;

	public GetBudgetsResponse withBudgets(List<Budget> budgets) {
		this.setBudgets(budgets);
		return this;
	}
}
