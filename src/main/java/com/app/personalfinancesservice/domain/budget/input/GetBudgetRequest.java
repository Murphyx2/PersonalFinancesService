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
public class GetBudgetRequest {

	private String id;
	private String userId;
	private String portfolioId;

	public GetBudgetRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public GetBudgetRequest withPortfolioId(String portfolioId) {
		this.setPortfolioId(portfolioId);
		return this;
	}

	public GetBudgetRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
