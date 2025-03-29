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
import com.app.personalfinancesservice.exceptions.InvalidUserIdException;
import com.app.personalfinancesservice.repository.PortfolioRepository;

@Service
public class PortfolioService implements PortfolioServiceBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

	private static final String EXCEPTION_LABEL = "CREATE_PORTFOLIO";
	private final PortfolioRepository repository;

	public PortfolioService(PortfolioRepository repository) {
		this.repository = repository;
	}

	@Override
	public CreatePortfolioResponse createPortfolio(CreatePortfolioRequest request) {

		//Validate UserID
		if (request.getUserId() == null) {
			throw new InvalidUserIdException(EXCEPTION_LABEL, "User id is required");
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
			throw new InvalidUserIdException(EXCEPTION_LABEL, userId);
		}

		return this.createPortfolio(request);
	}

	@Override
	public DeletePortfolioResponse deletePortfolio(DeletePortfolioRequest request) {
		return null;
	}

	@Override
	public GetPortfolioResponse getPortfolio(GetPortfolioRequest request) {

		GetPortfolioResponse response = new GetPortfolioResponse();
		try {
			UUID id = UUID.fromString(request.getPortfolioId());
			UUID userId = UUID.fromString(request.getUserId());
			response.withPortfolio(repository.getPortfolioByIdAndUserId(id, userId));
		}catch (IllegalArgumentException e){
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidUserIdException(EXCEPTION_LABEL, request.getPortfolioId());
		}

		return response;
	}

	@Override
	public GetAllPortfolioResponse getAllPortfolio(GetAllPortfolioRequest request) {

		GetAllPortfolioResponse response = new GetAllPortfolioResponse();
		try {
			UUID userId = UUID.fromString(request.getUserId());
			response.withPortfolios(repository.getAllByUserId(userId));

		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidUserIdException(EXCEPTION_LABEL, request.getUserId());
		}

		return response;
	}

	@Override
	public UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request) {
		return null;
	}
}
