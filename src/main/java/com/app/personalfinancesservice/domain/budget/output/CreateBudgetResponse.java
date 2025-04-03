package com.app.personalfinancesservice.domain.budget.output;

import com.app.personalfinancesservice.domain.budget.Budget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBudgetResponse {

	private Budget budget;

	public CreateBudgetResponse withBudget(Budget budget) {
		this.setBudget(budget);
		return this;
	}
}
