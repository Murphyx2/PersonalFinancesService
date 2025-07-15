package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;
import com.personalfinance.api.domain.portfolio.input.CreatePortfolioRequest;

public class PortfolioConverter {

	public static Portfolio convert(CreatePortfolioRequest request) {

		if (request == null) {
			return null;
		}

		Portfolio portfolio = new Portfolio() //
				.withUserId(UUIDConverter.convert(request.getUserId(), "userId", "PORTFOLIO")) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withCreated(LocalDateTime.now());

		if (request.getName() == null || request.getName().isBlank()) {
			portfolio.setName("Portfolio: " + UUID.randomUUID());
		}

		return portfolio;

	}

	public static Portfolio convert(PortfolioDTO request, Portfolio oldPortfolio) {

		if (request == null || oldPortfolio == null) {
			return null;
		}

		return oldPortfolio.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withBudgets(oldPortfolio.getBudgets()) //
				.withCreated(oldPortfolio.getCreated()) //
				.withUpdated(LocalDateTime.now());

	}

	private PortfolioConverter() {
		// Empty on purpose
	}
}
