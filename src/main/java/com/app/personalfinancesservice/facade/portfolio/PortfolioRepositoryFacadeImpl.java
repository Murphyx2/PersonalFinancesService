package com.app.personalfinancesservice.facade.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.converters.PortfolioDTOConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.repository.PortfolioRepository;
import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;
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
	@Caching(evict = { //
			@CacheEvict(value = "portfoliosList", key = "#portfolioDTO.userId"), //
			@CacheEvict(value = "portfolios", key = "#portfolioDTO.userId + '_' + #portfolioDTO.id") //
	})
	public boolean deletePortfolio(PortfolioDTO portfolioDTO) {

		Portfolio portfolio = portfolioRepository //
				.getPortfolioByIdAndUserId(portfolioDTO.getId(), portfolioDTO.getUserId()) //
				.orElse(null);

		if (portfolio != null) {
			portfolioRepository.delete(portfolio);
		}

		return true;
	}

	@Override
	public boolean existsPortfolio(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, PORTFOLIO_LABEL);

		UUID idUUID = UUIDConverter //
				.convert(id, PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

		return portfolioRepository.existsByIdAndUserId(idUUID, userIdUUID);
	}

	@Override
	@Cacheable(value = "portfoliosList", key = "#userId")
	public List<PortfolioDTO> getAllPortfolioByUserId(String userId) {

		if (userId == null) {
			throw new MissingIdException(PORTFOLIO_LABEL, USER_ID_LABEL);
		}

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, PORTFOLIO_LABEL);

		return PortfolioDTOConverter //
				.convertMany(portfolioRepository.getAllByUserId(userIdUUID));
	}

	@Override
	@Cacheable(value = "portfolios", key = "#userId + '_' + #id")
	public PortfolioDTO getPortfolioByIdAndUserId(String id, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, PORTFOLIO_LABEL);

		UUID idUUID = UUIDConverter //
				.convert(id, PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

		Optional<Portfolio> portfolio = portfolioRepository //
				.getPortfolioByIdAndUserId(idUUID, userIdUUID);

		return PortfolioDTOConverter //
				.convert(portfolio //
						.orElseThrow(() -> //
								new NotFoundException(PORTFOLIO_LABEL, PORTFOLIO_ID_LABEL, id)) //
				);
	}

	@Override
	@CacheEvict(value = "portfoliosList", key = "#portfolio.userId")
	@CachePut(value = "portfolios", key = "#portfolio.userId + '_' + #portfolio.id")
	public PortfolioDTO savePortfolio(Portfolio portfolio) {

		if (portfolio == null) {
			throw new NotFoundException(PORTFOLIO_LABEL, "portfolio");
		}

		return PortfolioDTOConverter //
				.convert(portfolioRepository.save(portfolio));
	}

	@Override
	@CacheEvict(value = "portfoliosList", key = "#portfolioDTO.userId")
	@CachePut(value = "portfolios", key = "#portfolioDTO.userId + '_' + #portfolioDTO.id")
	public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO) {

		// if null return empty
		Portfolio oldPortfolio = portfolioRepository //
				.getPortfolioByIdAndUserId(portfolioDTO.getId(), portfolioDTO.getUserId()) //
				.orElse(null);

		if (oldPortfolio == null) {
			throw new NotFoundException(PORTFOLIO_LABEL, "portfolioDTO");
		}

		Portfolio portfolio = PortfolioConverter //
				.convert(portfolioDTO, oldPortfolio);

		return PortfolioDTOConverter //
				.convert(portfolioRepository.save(portfolio));
	}
}
