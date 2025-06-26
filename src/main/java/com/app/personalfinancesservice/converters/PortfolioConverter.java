package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;
import com.personalfinance.api.domain.portfolio.input.CreatePortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.UpdatePortfolioRequest;

public class PortfolioConverter {

	private static final String PORTFOLIO_LABEL = "PORTFOLIO";

	public static Portfolio convert(CreatePortfolioRequest request) {

		Portfolio portfolio = new Portfolio() //
				.withUserId(UUIDConverter.convert(request.getUserId(), "userId", PORTFOLIO_LABEL)) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withCreated(LocalDateTime.now());

		if (request.getName() == null || request.getName().isBlank()) {
			portfolio.setName("Portfolio: " + UUID.randomUUID());
		}

		return portfolio;

	}

	public static Portfolio convert(UpdatePortfolioRequest request, PortfolioDTO oldPortfolio) {

		return new Portfolio() //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withBudgets(oldPortfolio.getBudgets()) //
				.withCreated(oldPortfolio.getCreated()) //
				.withUpdated(LocalDateTime.now());

	}

	private PortfolioConverter() {
		// Empty on purpose
	}
}
