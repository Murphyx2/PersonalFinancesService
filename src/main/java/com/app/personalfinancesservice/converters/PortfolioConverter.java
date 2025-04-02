package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.UpdatePortfolioRequest;

public class PortfolioConverter {

	public static Portfolio convert(CreatePortfolioRequest request) {

		Portfolio portfolio = new Portfolio() //
				.withUserId(request.getUserId()) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withCreated(LocalDateTime.now());

		if (request.getName() == null || request.getName().isBlank()) {
			portfolio.setName("Portfolio: " + UUID.randomUUID());
		}

		return portfolio;

	}

	public static Portfolio convert(UpdatePortfolioRequest request, Portfolio oldPortfolio) {

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
