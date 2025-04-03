package com.app.personalfinancesservice.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.DeletePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetAllPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.UpdatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.DeletePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetAllPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.UpdatePortfolioResponse;
import com.app.personalfinancesservice.domain.service.PortfolioServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewPortfolioException;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.repository.PortfolioRepository;

@Service
public class PortfolioService implements PortfolioServiceBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

	private static final String EXCEPTION_LABEL = "PORTFOLIO";
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
			throw new MissingIdException(EXCEPTION_LABEL, USER_ID_LABEL);
		}
		//Proceed to save the portfolio
		Portfolio portfolio;
		try {
			portfolio = repository.save(PortfolioConverter.convert(request));
		} catch (Exception e) {
			throw new CreateNewPortfolioException(EXCEPTION_LABEL, e.getMessage());
		}

		return new CreatePortfolioResponse().withPortfolio(portfolio);
	}

	public CreatePortfolioResponse createPortfolio(String userId, CreatePortfolioRequest request) {

		try {
			request.withUserId(UUID.fromString(userId));
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, USER_ID_LABEL, userId);
		}

		return this.createPortfolio(request);
	}

	@Override
	public DeletePortfolioResponse deletePortfolio(DeletePortfolioRequest request) {

		try {
			GetPortfolioRequest getRequest = new GetPortfolioRequest() //
					.withPortfolioId(request.getId()) //
					.withUserId(request.getUserId());
			repository.delete(getPortfolio(getRequest).getPortfolio());
		} catch (Exception e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			return new DeletePortfolioResponse().withSuccess(false);
		}
		return new DeletePortfolioResponse().withSuccess(true);
	}

	@Override
	public GetAllPortfolioResponse getAllPortfolio(GetAllPortfolioRequest request) {

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), USER_ID_LABEL, EXCEPTION_LABEL);

		return new GetAllPortfolioResponse() //
				.withPortfolios(repository.getAllByUserId(userId));
	}

	@Override
	public GetPortfolioResponse getPortfolio(GetPortfolioRequest request) {

		UUID id = UUIDConverter //
				.convert(request.getPortfolioId(), PORTFOLIO_ID_LABEL, EXCEPTION_LABEL);

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), USER_ID_LABEL, EXCEPTION_LABEL);

		return new GetPortfolioResponse() //
				.withPortfolio(repository.getPortfolioByIdAndUserId(id, userId));
	}

	@Override
	public UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request) {

		if (request.getId() == null || request.getId().isEmpty()) {
			throw new MissingIdException(EXCEPTION_LABEL, "portfolioId");
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
