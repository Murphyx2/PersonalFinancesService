package com.app.personalfinancesservice.facade.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.repository.PortfolioRepository;
import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.facade.PortfolioRepositoryFacade;

@Component
public class PortfolioRepositoryFacadeImpl implements PortfolioRepositoryFacade {

	private static final String PORTFOLIO_LABEL = "PORTFOLIO";
	private static final String USER_ID_LABEL = "userId";
	private static final String PORTFOLIO_ID_LABEL = "PortfolioID";

	private final PortfolioRepository portfolioRepository;

	PortfolioRepositoryFacadeImpl(PortfolioRepository portfolioRepository) {
		this.portfolioRepository = portfolioRepository;
	}

	@Override
	public boolean deletePortfolio(Portfolio portfolio) {

		portfolioRepository.delete(portfolio);

		return true;
	}

	@Override
	public List<Portfolio> getAllPortfolioByUserId(String userId) {

		if (userId == null) {
			throw new MissingIdException(PORTFOLIO_LABEL, USER_ID_LABEL);
		}

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, PORTFOLIO_LABEL);

		return portfolioRepository.getAllByUserId(userIdUUID);
	}

	@Override
	public Portfolio getPortfolioByIdAndUserId(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, PORTFOLIO_LABEL);

		UUID idUUID = UUIDConverter //
				.convert(id, PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

		Optional<Portfolio> portfolio = portfolioRepository.getPortfolioByIdAndUserId(idUUID, userIdUUID);

		return portfolio.orElseThrow(() -> new NotFoundException(PORTFOLIO_LABEL, PORTFOLIO_ID_LABEL, id));
	}

	@Override
	public Portfolio savePortfolio(Portfolio portfolio) {

		if(portfolio == null) {
			throw new CreateNewItemException(PORTFOLIO_LABEL, "portfolio");
		}

		//Validate UserID
		if (portfolio.getUserId() == null) {
			throw new MissingIdException(PORTFOLIO_LABEL, USER_ID_LABEL);
		}

		return portfolioRepository.save(portfolio);
	}

	@Override
	public boolean existsPortfolio(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, PORTFOLIO_LABEL);

		UUID idUUID = UUIDConverter //
				.convert(id, PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

		return portfolioRepository.existsByIdAndUserId(idUUID, userIdUUID);
	}
}
