package com.app.personalfinancesservice.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.DeletePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.UpdatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.DeletePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.UpdatePortfolioResponse;
import com.app.personalfinancesservice.domain.service.PortfolioServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewPortfolioException;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.filter.PortfolioSorter;
import com.app.personalfinancesservice.repository.PortfolioRepository;

@Service
public class PortfolioService implements PortfolioServiceBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

	private static final String PORTFOLIO_LABEL = "PORTFOLIO";
	private static final String USER_ID_LABEL = "userId";
	private static final String PORTFOLIO_ID_LABEL = "PortfolioID";

	private final PortfolioRepository repository;

	public PortfolioService(PortfolioRepository repository) {
		this.repository = repository;
	}

	@Override
	public CreatePortfolioResponse createPortfolio(CreatePortfolioRequest request) {

		//Validate UserID
		if (request.getUserId() == null) {
			throw new MissingIdException(PORTFOLIO_LABEL, USER_ID_LABEL);
		}
		//Proceed to save the portfolio
		Portfolio portfolio;
		try {
			portfolio = repository.save(PortfolioConverter.convert(request));
		} catch (Exception e) {
			throw new CreateNewPortfolioException(PORTFOLIO_LABEL, e.getMessage());
		}

		return new CreatePortfolioResponse().withPortfolio(portfolio);
	}

	public CreatePortfolioResponse createPortfolio(String userId, CreatePortfolioRequest request) {

		try {
			request.withUserId(UUID.fromString(userId));
		} catch (IllegalArgumentException e) {
			LOGGER.error(PORTFOLIO_LABEL, e);
			throw new InvalidIdException(PORTFOLIO_LABEL, USER_ID_LABEL, userId);
		}

		return this.createPortfolio(request);
	}

	@Override
	public DeletePortfolioResponse deletePortfolio(DeletePortfolioRequest request) {

		try {
			GetPortfolioRequest getRequest = new GetPortfolioRequest() //
					.withPortfolioId(request.getId()) //
					.withUserId(request.getUserId());
			repository.delete(getPortfolios(getRequest).getPortfolios().getFirst());
		} catch (Exception e) {
			LOGGER.error(PORTFOLIO_LABEL, e);
			return new DeletePortfolioResponse().withSuccess(false);
		}
		return new DeletePortfolioResponse().withSuccess(true);
	}

	@Override
	public GetPortfolioResponse getPortfolios(GetPortfolioRequest request) {

		List<Portfolio> portfolios;

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), USER_ID_LABEL, PORTFOLIO_LABEL);

		// Return all portfolio from User
		if (request.getPortfolioId() == null) {
			portfolios = repository.getAllByUserId(userId);
		} else {

			UUID id = UUIDConverter //
					.convert(request.getPortfolioId(), PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

			portfolios = repository.getPortfolioByIdAndUserId(id, userId);
		}

		// Apply filter here
		List<Portfolio> sortedPortfolios = PortfolioSorter //
				.sort(portfolios, request.getSortBy(), request.getSortDirection());

		return new GetPortfolioResponse().withPortfolios(sortedPortfolios);
	}

	@Override
	public UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request) {

		if (request.getId() == null || request.getId().isEmpty()) {
			throw new MissingIdException(PORTFOLIO_LABEL, "portfolioId");
		}

		// if null return empty
		GetPortfolioResponse oldPortfolio = getPortfolios(new GetPortfolioRequest() //
				.withPortfolioId(request.getId())//
				.withUserId(request.getUserId()) //
		);

		if (oldPortfolio == null || oldPortfolio.getPortfolios().isEmpty()) {
			return new UpdatePortfolioResponse();
		}

		Portfolio portfolio = PortfolioConverter //
				.convert(request, oldPortfolio.getPortfolios().getFirst()) //
				.withId(oldPortfolio.getPortfolios().getFirst().getId()) //
				.withUserId(oldPortfolio.getPortfolios().getFirst().getUserId()) //
				;

		return new UpdatePortfolioResponse() //
				.withPortfolio(repository.save(portfolio));
	}
}
