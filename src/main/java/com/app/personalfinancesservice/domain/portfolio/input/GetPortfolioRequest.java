package com.app.personalfinancesservice.domain.portfolio.input;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPortfolioRequest {

	private String userId;
	private String portfolioId;

	private SortBy sortBy;
	private SortDirection sortDirection;

	public GetPortfolioRequest withPortfolioId(String portfolioId) {
		this.setPortfolioId(portfolioId);
		return this;
	}

	public GetPortfolioRequest withSortBy(SortBy sortBy) {
		this.setSortBy(sortBy);
		return this;
	}

	public GetPortfolioRequest withSortDirection(SortDirection sortDirection) {
		this.setSortDirection(sortDirection);
		return this;
	}

	public GetPortfolioRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
