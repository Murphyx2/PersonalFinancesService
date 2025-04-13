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
public class GetCategoryPlannersRequest {

	private String userId;
	private String budgetId;

	private SortBy sortBy;
	private SortDirection sortDirection;
	private TransactionType transactionType;

	public GetCategoryPlannersRequest withBudgetId(String budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public GetCategoryPlannersRequest withSortBy(SortBy sortBy) {
		this.setSortBy(sortBy);
		return this;
	}

	public GetCategoryPlannersRequest withSortDirection(SortDirection sortDirection) {
		this.setSortDirection(sortDirection);
		return this;
	}

	public GetCategoryPlannersRequest withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public GetCategoryPlannersRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
