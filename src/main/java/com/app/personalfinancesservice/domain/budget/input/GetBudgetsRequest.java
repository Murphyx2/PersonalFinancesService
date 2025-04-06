package com.app.personalfinancesservice.domain.budget.input;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBudgetsRequest {

	private String id;
	private String userId;

	private SortBy sortBy;
	private SortDirection sortDirection;

	public GetBudgetsRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public GetBudgetsRequest withSortBy(SortBy sortBy) {
		this.setSortBy(sortBy);
		return this;
	}

	public GetBudgetsRequest withSortDirection(SortDirection sortDirection) {
		this.setSortDirection(sortDirection);
		return this;
	}

	public GetBudgetsRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
