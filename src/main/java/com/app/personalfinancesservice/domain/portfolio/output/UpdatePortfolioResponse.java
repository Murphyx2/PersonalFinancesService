package com.app.personalfinancesservice.domain.portfolio.output;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePortfolioResponse {

	private Portfolio portfolio;

	public UpdatePortfolioResponse withPortfolio(Portfolio portfolio) {
		this.setPortfolio(portfolio);
		return this;
	}
}
