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
public class GetBudgetResponse {

	private List<Budget> budget;

	public GetBudgetResponse withBudget(List<Budget> budget) {
		this.setBudget(budget);
		return this;
	}
}
