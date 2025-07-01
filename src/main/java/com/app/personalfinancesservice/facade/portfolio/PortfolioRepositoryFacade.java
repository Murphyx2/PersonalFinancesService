package com.app.personalfinancesservice.facade.portfolio;

import java.util.List;

import org.springframework.stereotype.Component;

import com.personalfinance.api.domain.portfolio.Portfolio;

@Component
public interface PortfolioRepositoryFacade {

	boolean deletePortfolio(Portfolio portfolio);

	boolean existsPortfolio(String id, String userId);

	List<Portfolio> getAllPortfolioByUserId(String userId);

	Portfolio getPortfolioByIdAndUserId(String id, String userId);

	Portfolio savePortfolio(Portfolio portfolio);
}
