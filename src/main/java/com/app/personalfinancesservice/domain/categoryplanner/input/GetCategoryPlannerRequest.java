package com.app.personalfinancesservice.domain.categoryplanner.input;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoryPlannerRequest {

	private String id;
	private String userId;
	private String budgetId;

	private SortBy sortBy;
	private SortDirection sortDirection;
	private TransactionType transactionType;

	public GetCategoryPlannerRequest withBudgetId(String budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public GetCategoryPlannerRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public GetCategoryPlannerRequest withSortBy(SortBy sortBy) {
		this.setSortBy(sortBy);
		return this;
	}

	public GetCategoryPlannerRequest withSortDirection(SortDirection sortDirection) {
		this.setSortDirection(sortDirection);
		return this;
	}

	public GetCategoryPlannerRequest withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public GetCategoryPlannerRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
