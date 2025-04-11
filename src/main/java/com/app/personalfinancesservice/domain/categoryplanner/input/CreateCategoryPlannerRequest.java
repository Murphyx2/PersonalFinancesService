package com.app.personalfinancesservice.domain.categoryplanner.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryPlannerRequest {

	private String userId;
	private String categoryId;
	private String budgetId;
	private Double plannedAmount;

	public CreateCategoryPlannerRequest withBudgetId(String budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public CreateCategoryPlannerRequest withCategoryId(String categoryId) {
		this.setCategoryId(categoryId);
		return this;
	}

	public CreateCategoryPlannerRequest withPlannedAmount(Double plannedAmount) {
		this.setPlannedAmount(plannedAmount);
		return this;
	}

	public CreateCategoryPlannerRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
