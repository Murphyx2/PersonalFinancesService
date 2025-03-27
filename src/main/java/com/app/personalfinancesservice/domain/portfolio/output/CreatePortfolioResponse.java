package com.app.personalfinancesservice.domain.portfolio.output;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortfolioResponse {

	private Portfolio portfolio;

	public CreatePortfolioResponse withPortfolio(Portfolio portfolio) {
		this.setPortfolio(portfolio);
		return this;
	}
}
