package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;

public class PortfolioConverter {

	public static Portfolio convert(CreatePortfolioRequest request) {

		Portfolio portfolio = new Portfolio() //
				.withUserId(request.getUserId()).withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withCreated(LocalDateTime.now());

		if (request.getName() == null || request.getName().isBlank()) {
			portfolio.setName("Portfolio: " + UUID.randomUUID());
		}

		return portfolio;

	}

	private PortfolioConverter() {
		// Empty on purpose
	}
}
