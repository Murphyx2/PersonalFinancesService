package com.app.personalfinancesservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.converters.PortfolioDTOConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.facade.portfolio.PortfolioRepositoryFacade;
import com.app.personalfinancesservice.filter.PortfolioSorter;
import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.input.CreatePortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.DeletePortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.GetPortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.GetPortfoliosRequest;
import com.personalfinance.api.domain.portfolio.input.UpdatePortfolioRequest;
import com.personalfinance.api.domain.portfolio.output.CreatePortfolioResponse;
import com.personalfinance.api.domain.portfolio.output.DeletePortfolioResponse;
import com.personalfinance.api.domain.portfolio.output.GetPortfolioResponse;
import com.personalfinance.api.domain.portfolio.output.GetPortfoliosResponse;
import com.personalfinance.api.domain.portfolio.output.UpdatePortfolioResponse;
import com.personalfinance.api.service.PortfolioServiceBase;

@Service
public class PortfolioService implements PortfolioServiceBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

	private static final String PORTFOLIO_LABEL = "PORTFOLIO";

	private final PortfolioRepositoryFacade portfolioRepositoryFacade;

	public PortfolioService(PortfolioRepositoryFacade portfolioRepositoryFacade) {
		this.portfolioRepositoryFacade = portfolioRepositoryFacade;
	}

	@Override
	public CreatePortfolioResponse createPortfolio(CreatePortfolioRequest request) {

		//Proceed to save the portfolio
		Portfolio portfolio;
		try {
			portfolio = portfolioRepositoryFacade //
					.savePortfolio(PortfolioConverter.convert(request));
		} catch (Exception e) {
			throw new CreateNewItemException("Portfolio", request.getName(), PORTFOLIO_LABEL);
		}

		return new CreatePortfolioResponse() //
				.withPortfolio(PortfolioDTOConverter.convert(portfolio));
	}

	@Override
	public DeletePortfolioResponse deletePortfolio(DeletePortfolioRequest request) {

		boolean result = false;
		try {
			Portfolio portfolio = portfolioRepositoryFacade //
					.getPortfolioByIdAndUserId(request.getId(), request.getUserId());

			result = portfolioRepositoryFacade.deletePortfolio(portfolio);
		} catch (Exception e) {
			LOGGER.error(PORTFOLIO_LABEL, e);
			return new DeletePortfolioResponse().withSuccess(result);
		}
		return new DeletePortfolioResponse().withSuccess(result);
	}

	@Override
	public GetPortfolioResponse getPortfolio(GetPortfolioRequest request) {

		return new GetPortfolioResponse() //
				.withPortfolio(PortfolioDTOConverter //
						.convert(portfolioRepositoryFacade //
								.getPortfolioByIdAndUserId(request.getPortfolioId() //
										, request.getUserId()))//
				);
	}

	@Override
	public GetPortfoliosResponse getPortfolios(GetPortfoliosRequest request) {

		// Return all portfolios from User
		List<Portfolio> portfolios = portfolioRepositoryFacade //
				.getAllPortfolioByUserId(request.getUserId());

		// Apply filter here
		List<Portfolio> sortedPortfolios = PortfolioSorter //
				.sort(portfolios, request.getSortBy(), request.getSortDirection());

		return new GetPortfoliosResponse() //
				.withPortfolios(PortfolioDTOConverter.convertMany(sortedPortfolios));
	}

	@Override
	public UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request) {

		if (request.getId() == null || request.getId().isEmpty()) {
			throw new MissingIdException(PORTFOLIO_LABEL, "portfolioId");
		}

		// if null return empty
		Portfolio oldPortfolio = portfolioRepositoryFacade //
				.getPortfolioByIdAndUserId(request.getId(), request.getUserId());

		if (oldPortfolio == null) {
			return new UpdatePortfolioResponse();
		}

		Portfolio updatedPortfolio = portfolioRepositoryFacade //
				.savePortfolio(PortfolioConverter.convert(request, oldPortfolio));

		return new UpdatePortfolioResponse() //
				.withPortfolio(PortfolioDTOConverter.convert(updatedPortfolio));
	}
}
