package com.app.personalfinancesservice.domain.transaction.input;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetListTransactionRequest {

	private String userId;
	private String budgetId;

	// Optional Parameters
	private SortBy sortBy;
	private SortDirection sortDirection;
	private String categoryName;
	private TransactionType transactionType;

	public GetListTransactionRequest withBudgetId(String budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public GetListTransactionRequest withCategoryName(String categoryName) {
		setCategoryName(categoryName);
		return this;
	}

	public GetListTransactionRequest withSortBy(SortBy sortBy) {
		this.setSortBy(sortBy);
		return this;
	}

	public GetListTransactionRequest withSortDirection(SortDirection sortDirection) {
		this.setSortDirection(sortDirection);
		return this;
	}

	public GetListTransactionRequest withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public GetListTransactionRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
