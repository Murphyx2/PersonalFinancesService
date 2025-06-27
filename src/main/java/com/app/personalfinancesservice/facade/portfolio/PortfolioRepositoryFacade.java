package com.app.personalfinancesservice.facade.portfolio;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.portfolio.Portfolio;

@Repository
public interface PortfolioRepositoryFacade {

	boolean deletePortfolio(Portfolio portfolio);

	List<Portfolio> getAllPortfolioByUserId(String userId);

	Portfolio getPortfolioByIdAndUserId(String id, String userId);

	Portfolio savePortfolio(Portfolio portfolio);

	boolean existsPortfolio(String id, String userId);
}
