package com.app.personalfinancesservice.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;

public class PortfolioDTOConverter {

	public static PortfolioDTO convert(Portfolio portfolio) {

		if(portfolio == null) {
			return null;
		}

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

	public static List<PortfolioDTO> convertMany(List<Portfolio> portfolios) {
		if(portfolios == null || portfolios.isEmpty()) {
			return new ArrayList<>();
		}
		return portfolios //
				.stream() //
				.map(PortfolioDTOConverter::convert) //
				.toList();
	}

	private PortfolioDTOConverter() {
		// Empty on purpose
	}
}
