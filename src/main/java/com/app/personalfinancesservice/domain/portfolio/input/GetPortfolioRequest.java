package com.app.personalfinancesservice.domain.portfolio.input;

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

	public GetPortfolioRequest withPortfolioId(String portfolioId) {
		this.setPortfolioId(portfolioId);
		return this;
	}

	public GetPortfolioRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
