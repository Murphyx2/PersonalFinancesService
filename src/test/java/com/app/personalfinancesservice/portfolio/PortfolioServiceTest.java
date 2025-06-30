package com.app.personalfinancesservice.portfolio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.facade.portfolio.PortfolioRepositoryFacade;
import com.app.personalfinancesservice.service.PortfolioService;
import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.input.CreatePortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.GetPortfoliosRequest;
import com.personalfinance.api.domain.portfolio.output.CreatePortfolioResponse;
import com.personalfinance.api.domain.portfolio.output.GetPortfoliosResponse;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

	@Mock
	private PortfolioRepositoryFacade portfolioRepository;
	@InjectMocks
	private PortfolioService portfolioService;

	@Test
	void createPortfolioMissingUserId() {
		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(null);

		CreateNewItemException exception = assertThrows(CreateNewItemException.class, () -> {
			portfolioService.createPortfolio(request);
		});

		assertEquals("Error creating Portfolio, null", exception.getMessage());
		assertEquals("PORTFOLIO", exception.getLocation());
	}

	@Test
	void createPortfolioRepositoryFailure() {
		String validUserId = "550e8400-e29b-41d4-a716-446655440000";
		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(validUserId);

		when(portfolioRepository.savePortfolio(any(Portfolio.class))) //
				.thenThrow(new RuntimeException("Database error"));

		CreateNewItemException exception = assertThrows(CreateNewItemException.class, () -> {
			portfolioService.createPortfolio(request.withUserId(validUserId));
		});

		assertEquals(String.format("Error creating %s, %s", "Portfolio", null), exception.getMessage());
		assertEquals("Portfolio", exception.getFieldName());
		assertNull(exception.getFieldValue());
	}

	@Test
	void createPortfolioRepositorySuccess() {
		//Arrange
		String validUserId = "550e8400-e29b-41d4-a716-446655440000";
		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(validUserId) //
				.withName("New Portfolio") //
				.withDescription("This is the description of the portfolio") //
				.withCreated(LocalDateTime.now()) //
				;

		Portfolio portfolio = PortfolioConverter.convert(request);

		// Configure the mock to return portfolio went saving
		when(portfolioRepository //
				.savePortfolio(any(Portfolio.class))) //
				.thenReturn(portfolio);

		// Execute
		CreatePortfolioResponse response = portfolioService.createPortfolio(request);

		// Assert
		verify(portfolioRepository, times(1)).savePortfolio(any(Portfolio.class));

		assertEquals(portfolio.getId(), response.getPortfolio().getId());
		assertEquals(portfolio.getName(), response.getPortfolio().getName());
		assertEquals(portfolio.getDescription(), response.getPortfolio().getDescription());
		assertEquals(portfolio.getCreated(), response.getPortfolio().getCreated());

	}

	@Test
	void createPortfolioWithInvalidUserId() {
		String invalidUserId = "invalidUserId";
		CreatePortfolioRequest request = new CreatePortfolioRequest();

		CreateNewItemException exception = assertThrows(CreateNewItemException.class, () -> {
			portfolioService.createPortfolio(request.withUserId(invalidUserId));
		});

		assertEquals("Error creating Portfolio, null", exception.getMessage());
		assertEquals("PORTFOLIO", exception.getLocation());
	}

	@Test
	void getPortfoliosNotFound() {
		UUID validUserId = UUID.randomUUID();
		UUID unknownPortfolioId = UUID.randomUUID();

		GetPortfoliosRequest request = new GetPortfoliosRequest() //
				.withPortfolioId(unknownPortfolioId.toString()) //
				.withUserId(validUserId.toString());

		when(portfolioRepository.getAllPortfolioByUserId(any())).thenReturn(null);

		GetPortfoliosResponse response = portfolioService.getPortfolios(request);

		assertNotNull(response.getPortfolios());
		assertTrue(response.getPortfolios().isEmpty());
	}

	@Test
	void getPortfoliosSuccess() {
		UUID validUserId = UUID.randomUUID();
		UUID validPortfolioId = UUID.randomUUID();

		Portfolio portfolio = new Portfolio() //
				.withId(validUserId) //
				.withUserId(validPortfolioId) //
				.withName("Test name Portfolio") //
				.withDescription("Test description portfolio") //
				.withBudgets(new ArrayList<>()).withCreated(LocalDateTime.now());

		List<Portfolio> portfolios = new ArrayList<>();
		portfolios.add(portfolio);

		GetPortfoliosRequest request = new GetPortfoliosRequest() //
				.withPortfolioId(validPortfolioId.toString()) //
				.withUserId(validUserId.toString()) //
				.withSortBy(SortBy.CREATED_AT) //
				.withSortDirection(SortDirection.ASC);

		when(portfolioRepository.getAllPortfolioByUserId(any())).thenReturn(portfolios);

		GetPortfoliosResponse response = portfolioService.getPortfolios(request);

		assertEquals(portfolio.getId(), response.getPortfolios().getFirst().getId());
		assertEquals(portfolio.getName(), response.getPortfolios().getFirst().getName());
		assertEquals(portfolio.getDescription(), response.getPortfolios().getFirst().getDescription());
		assertEquals(portfolio.getCreated(), response.getPortfolios().getFirst().getCreated());
	}
}
