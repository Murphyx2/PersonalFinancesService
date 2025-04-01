package com.app.personalfinancesservice.portfolio;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.converters.PortfolioConverter;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.exceptions.CreateNewPortfolioException;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
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

		assertEquals(portfolio.getId(), response.getId());
		assertEquals(portfolio.getName(), response.getName());
		assertEquals(portfolio.getDescription(), response.getDescription());
		assertEquals(portfolio.getCreated(), response.getCreated());

	}

	@Test
	void createPortfolioWithInvalidUserId() {
		String invalidUserId = "invalidUserId";
		CreatePortfolioRequest request = new CreatePortfolioRequest();

		InvalidIdException exception = assertThrows(InvalidIdException.class, () -> {
			portfolioService.createPortfolio(invalidUserId, request);
		});

		assertEquals(String.format("Invalid userId %s", invalidUserId), exception.getMessage());
		assertEquals("PORTFOLIO", exception.getLocation());
		assertEquals(invalidUserId, exception.getFieldValue());
	}
}
