package com.app.personalfinancesservice.domain.category.input;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoryRequest {

	private String id;
	private String userId;
	private SortBy sortBy;
	private SortDirection sortDirection;
	private TransactionType transactionType;

	public GetCategoryRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public GetCategoryRequest withSortBy(SortBy sortBy) {
		this.setSortBy(sortBy);
		return this;
	}

	public GetCategoryRequest withSortDirection(SortDirection sortDirection) {
		this.setSortDirection(sortDirection);
		return this;
	}

	public GetCategoryRequest withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public GetCategoryRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
