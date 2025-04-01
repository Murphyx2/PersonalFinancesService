package com.app.personalfinancesservice.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.PortfolioConverter;
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
import com.app.personalfinancesservice.exceptions.PortfolioNotFoundException;
import com.app.personalfinancesservice.repository.PortfolioRepository;

@Service
public class PortfolioService implements PortfolioServiceBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

	private static final String EXCEPTION_LABEL = "PORTFOLIO";
	private static final String USER_ID_LABEL = "userId";
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
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new CreateNewPortfolioException(EXCEPTION_LABEL, e.getMessage());
		}

		return PortfolioConverter.convert(portfolio);
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
		return null;
	}

	@Override
	public GetAllPortfolioResponse getAllPortfolio(GetAllPortfolioRequest request) {

		GetAllPortfolioResponse response = new GetAllPortfolioResponse();
		try {
			UUID userId = UUID.fromString(request.getUserId());
			response.withPortfolios(repository.getAllByUserId(userId));
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, USER_ID_LABEL, request.getUserId());
		}

		if (response.getPortfolios() == null || response.getPortfolios().isEmpty()) {
			throw new PortfolioNotFoundException(EXCEPTION_LABEL, USER_ID_LABEL, request.getUserId());
		}

		return response;
	}

	@Override
	public GetPortfolioResponse getPortfolio(GetPortfolioRequest request) {

		GetPortfolioResponse response = new GetPortfolioResponse();
		UUID id;
		// Is there a better way to do this?, Research a little
		try {
			id = UUID.fromString(request.getPortfolioId());
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, "PortfolioID", request.getPortfolioId());
		}
		try {
			UUID userId = UUID.fromString(request.getUserId());
			response.withPortfolio(repository.getPortfolioByIdAndUserId(id, userId));
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, USER_ID_LABEL, request.getUserId());
		}

		if (response.getPortfolio() == null) {
			throw new PortfolioNotFoundException(EXCEPTION_LABEL, "id", request.getPortfolioId());
		}

		return response;
	}

	@Override
	public UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request) {

		if (request.getId() == null || request.getId().isEmpty()) {
			throw new MissingIdException(EXCEPTION_LABEL, "portfolioId");
		}

		UpdatePortfolioResponse response = new UpdatePortfolioResponse();
		UUID id;
		UUID userId;

		// Check if portfolio id or UserID are UUID
		try {
			id = UUID.fromString(request.getId());
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, "PortfolioID", request.getId());
		}

		try {
			userId = UUID.fromString(request.getUserId());
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, USER_ID_LABEL, request.getUserId());
		}

		// if not found, an exception is expected
		GetPortfolioResponse oldPortfolio = getPortfolio(new GetPortfolioRequest() //
				.withPortfolioId(request.getId())//
				.withUserId(request.getUserId()) //
		);

		Portfolio portfolio = PortfolioConverter //
				.convert(request, oldPortfolio.getPortfolio()) //
				.withId(id) //
				.withUserId(userId) //
				;
		try {
			response.withPortfolio(repository.save(portfolio));
		} catch (RuntimeException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new RuntimeException("Unable to save Portfolio", e);
		}

		return response;
	}
}
