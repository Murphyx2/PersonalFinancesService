package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;
import com.personalfinance.api.domain.portfolio.input.CreatePortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.UpdatePortfolioRequest;

public class PortfolioDTOConverter {

	private static final String PORTFOLIO_LABEL = "PORTFOLIO";
	private static final String USER_ID_LABEL = "userId";
	private static final String PORTFOLIO_ID_LABEL = "PortfolioID";

	public static PortfolioDTO convert(Portfolio portfolio) {

		if (portfolio == null) {
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

	public static PortfolioDTO convert(CreatePortfolioRequest request) {

		if (request == null) {
			return null;
		}

		PortfolioDTO portfolio = new PortfolioDTO() //
				.withUserId(UUIDConverter //
						.convert(request.getUserId(), USER_ID_LABEL, PORTFOLIO_LABEL)) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				.withCreated(LocalDateTime.now());

		if (request.getName() == null || request.getName().isBlank()) {
			portfolio.setName("Portfolio: " + UUID.randomUUID());
		}

		return portfolio;

	}

	public static PortfolioDTO convert(UpdatePortfolioRequest request) {
		if (request == null) {
			return null;
		}

		UUID userUUID = UUIDConverter //
				.convert(request.getUserId(), USER_ID_LABEL, PORTFOLIO_LABEL);

		UUID idUUID = UUIDConverter //
				.convert(request.getId(), PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

		return new PortfolioDTO() //
				.withId(idUUID) //
				.withUserId(userUUID) //
				.withName(request.getName()) //
				.withDescription(request.getDescription()) //
				;
	}

	public static List<PortfolioDTO> convertMany(List<Portfolio> portfolios) {
		if (portfolios == null || portfolios.isEmpty()) {
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
