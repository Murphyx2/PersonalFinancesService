package com.app.personalfinancesservice.converters;

import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;

public class PortfolioDTOConverter {

	public static PortfolioDTO convert(Portfolio portfolio) {
		return new PortfolioDTO() //
				.withId(portfolio.getId()) //
				.withUserId(portfolio.getUserId()) //
				.withName(portfolio.getName()) //
				.withDescription(portfolio.getDescription()) //
				.withBudgets(BudgetDTOConverter //
						.convertMany(portfolio.getBudgets()) //
				) //
				.withCreated(portfolio.getCreated()) //
				.withUpdated(portfolio.getUpdated()) //
				;
	}

	private PortfolioDTOConverter() {
		// Empty on purpose
	}
}
