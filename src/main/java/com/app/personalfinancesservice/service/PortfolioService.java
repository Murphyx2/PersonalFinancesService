package com.app.personalfinancesservice.service;

import java.util.List;
import java.util.Optional;
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
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfoliosRequest;
import com.app.personalfinancesservice.domain.portfolio.input.UpdatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.DeletePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfoliosResponse;
import com.app.personalfinancesservice.domain.portfolio.output.UpdatePortfolioResponse;
import com.app.personalfinancesservice.domain.service.PortfolioServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
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
			throw new CreateNewItemException("Portfolio", request.getName(), PORTFOLIO_LABEL);
		}

		return new CreatePortfolioResponse().withPortfolio(portfolio);
	}

	@Override
	public DeletePortfolioResponse deletePortfolio(DeletePortfolioRequest request) {

		try {
			GetPortfolioRequest getRequest = new GetPortfolioRequest() //
					.withPortfolioId(request.getId()) //
					.withUserId(request.getUserId());
			repository.delete(getPortfolio(getRequest).getPortfolio());
		} catch (Exception e) {
			LOGGER.error(PORTFOLIO_LABEL, e);
			return new DeletePortfolioResponse().withSuccess(false);
		}
		return new DeletePortfolioResponse().withSuccess(true);
	}

	@Override
	public GetPortfolioResponse getPortfolio(GetPortfolioRequest request) {
		UUID userId = UUIDConverter //
				.convert(request.getUserId(), USER_ID_LABEL, PORTFOLIO_LABEL);

		UUID id = UUIDConverter //
				.convert(request.getPortfolioId(), PORTFOLIO_ID_LABEL, PORTFOLIO_LABEL);

		Optional<Portfolio> portfolio = repository.getPortfolioByIdAndUserId(id, userId);

		return new GetPortfolioResponse() //
				.withPortfolio(portfolio.orElseThrow( //
								() -> new NotFoundException(PORTFOLIO_LABEL, PORTFOLIO_ID_LABEL, request.getPortfolioId()) //
						) //
				);
	}

	@Override
	public GetPortfoliosResponse getPortfolios(GetPortfoliosRequest request) {

		List<Portfolio> portfolios;

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), USER_ID_LABEL, PORTFOLIO_LABEL);

		// Return all portfolios from User
		portfolios = repository.getAllByUserId(userId);

		// Apply filter here
		List<Portfolio> sortedPortfolios = PortfolioSorter //
				.sort(portfolios, request.getSortBy(), request.getSortDirection());

		return new GetPortfoliosResponse().withPortfolios(sortedPortfolios);
	}

	@Override
	public UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request) {

		if (request.getId() == null || request.getId().isEmpty()) {
			throw new MissingIdException(PORTFOLIO_LABEL, "portfolioId");
		}

		// if null return empty
		GetPortfolioResponse oldPortfolio = getPortfolio(new GetPortfolioRequest() //
				.withPortfolioId(request.getId())//
				.withUserId(request.getUserId()) //
		);

		if (oldPortfolio == null) {
			return new UpdatePortfolioResponse();
		}

		Portfolio portfolio = PortfolioConverter //
				.convert(request, oldPortfolio.getPortfolio()) //
				.withId(oldPortfolio.getPortfolio().getId()) //
				.withUserId(oldPortfolio.getPortfolio().getUserId()) //
				;

		return new UpdatePortfolioResponse() //
				.withPortfolio(repository.save(portfolio));
	}
}
