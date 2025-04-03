package com.app.personalfinancesservice.portfolio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.exceptions.CreateNewPortfolioException;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.exceptions.PortfolioNotFoundException;
import com.app.personalfinancesservice.repository.PortfolioRepository;
import com.app.personalfinancesservice.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

	private static final String USERID_LABEL = "userId";
	@Mock
	private PortfolioRepository portfolioRepository;
	@InjectMocks
	private PortfolioService portfolioService;

	@Test
	void createPortfolioMissingUserId() {
		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(null);

		MissingIdException exception = assertThrows(MissingIdException.class, () -> {
			portfolioService.createPortfolio(request);
		});

		assertEquals("Missing userId", exception.getMessage());
		assertEquals("PORTFOLIO", exception.getLocation());
	}

	@Test
	void createPortfolioRepositoryFailure() {
		String validUserId = "550e8400-e29b-41d4-a716-446655440000";
		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(UUID.fromString(validUserId));

		when(portfolioRepository.save(any(Portfolio.class))).thenThrow(new RuntimeException("Database error"));

		CreateNewPortfolioException exception = assertThrows(CreateNewPortfolioException.class, () -> {
			portfolioService.createPortfolio(validUserId, request);
		});

		assertEquals("Error creating portfolio", exception.getMessage());
		assertEquals("PORTFOLIO", exception.getFieldName());
		assertEquals("Database error", exception.getFieldValue());
	}

	@Test
	void createPortfolioRepositorySuccess() {
		//Arrange
		String validUserId = "550e8400-e29b-41d4-a716-446655440000";
		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(UUID.fromString(validUserId)) //
				.withName("New Portfolio") //
				.withDescription("This is the description of the portfolio") //
				.withCreated(LocalDateTime.now()) //
				;

		Portfolio portfolio = PortfolioConverter.convert(request);

		// Configure the mock to return portfolio went saving
		when(portfolioRepository.save(any(Portfolio.class))).thenReturn(portfolio);

		// Execute
		CreatePortfolioResponse response = portfolioService.createPortfolio(validUserId, request);

		// Assert
		verify(portfolioRepository, times(1)).save(any(Portfolio.class));

		assertEquals(portfolio.getId(), response.getPortfolio().getId());
		assertEquals(portfolio.getName(), response.getPortfolio().getName());
		assertEquals(portfolio.getDescription(), response.getPortfolio().getDescription());
		assertEquals(portfolio.getCreated(), response.getPortfolio().getCreated());

	}

	@Test
	void createPortfolioWithInvalidUserId() {
		String invalidUserId = "invalidUserId";
		CreatePortfolioRequest request = new CreatePortfolioRequest();

		InvalidIdException exception = assertThrows(InvalidIdException.class, () -> {
			portfolioService.createPortfolio(invalidUserId, request);
		});

		assertEquals(String.format("Invalid %s %s", USERID_LABEL, invalidUserId), exception.getMessage());
		assertEquals("PORTFOLIO", exception.getLocation());
		assertEquals(invalidUserId, exception.getFieldValue());
	}

	@Test
	void getPortfolioNotFound() {
		UUID validUserId = UUID.randomUUID();
		UUID unknownPortfolioId = UUID.randomUUID();

		GetPortfolioRequest request = new GetPortfolioRequest() //
				.withPortfolioId(unknownPortfolioId.toString()) //
				.withUserId(validUserId.toString());

		when(portfolioRepository.getPortfolioByIdAndUserId(any(UUID.class), any(UUID.class))).thenReturn(null);

		PortfolioNotFoundException notFoundException = assertThrows(PortfolioNotFoundException.class, () -> portfolioService.getPortfolio(request));

		// Do I really need to do that?
		assertEquals(String.format("Portfolio from %s %s not found", "id", unknownPortfolioId.toString()), notFoundException.getMessage());
		assertEquals("PORTFOLIO", notFoundException.getLocation());
		assertEquals(unknownPortfolioId.toString(), notFoundException.getFieldValue());
	}

	@Test
	void getPortfolioSuccess() {
		UUID validUserId = UUID.randomUUID();
		UUID validPortfolioId = UUID.randomUUID();

		Portfolio portfolio = new Portfolio() //
				.withId(validUserId) //
				.withUserId(validPortfolioId) //
				.withName("Test name Portfolio") //
				.withDescription("Test description portfolio") //
				.withBudgets(new ArrayList<>()).withCreated(LocalDateTime.now());

		GetPortfolioRequest request = new GetPortfolioRequest() //
				.withPortfolioId(validPortfolioId.toString()) //
				.withUserId(validUserId.toString());

		when(portfolioRepository.getPortfolioByIdAndUserId(any(UUID.class), any(UUID.class))).thenReturn(portfolio);

		GetPortfolioResponse response = portfolioService.getPortfolio(request);

		assertEquals(portfolio.getId(), response.getPortfolio().getId());
		assertEquals(portfolio.getName(), response.getPortfolio().getName());
		assertEquals(portfolio.getDescription(), response.getPortfolio().getDescription());
		assertEquals(portfolio.getCreated(), response.getPortfolio().getCreated());
	}
}
