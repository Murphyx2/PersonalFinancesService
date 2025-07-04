package com.app.personalfinancesservice.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.app.personalfinancesservice.converters.PortfolioConverter;

import com.personalfinance.api.domain.portfolio.Portfolio;
import com.personalfinance.api.domain.portfolio.input.CreatePortfolioRequest;
import com.personalfinance.api.domain.portfolio.input.UpdatePortfolioRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioConverterTest {

	@Test
	void testConvertCreatePortfolioSuccess() {

		String userId = "caaa8fba-8b90-43e9-a261-a451f305c966";
		LocalDateTime dateTime = LocalDateTime.now();

		CreatePortfolioRequest request = new CreatePortfolioRequest() //
				.withUserId(userId) //
				.withName("Test Portfolio") //
				.withDescription("Test Description");

		Portfolio portfolio = PortfolioConverter.convert(request);

		assertEquals(request.getUserId(), portfolio.getUserId().toString());
		assertEquals(request.getName(), portfolio.getName());
		assertEquals(request.getDescription(), portfolio.getDescription());
		assertEquals(dateTime.getYear(), portfolio.getCreated().getYear());
		assertEquals(dateTime.getMonth(), portfolio.getCreated().getMonth());
		assertEquals(dateTime.getDayOfMonth(), portfolio.getCreated().getDayOfMonth());
		assertEquals(dateTime.getHour(), portfolio.getCreated().getHour());
		assertEquals(dateTime.getMinute(), portfolio.getCreated().getMinute());
	}

	@Test
	void testConvertUpdatePortfolioRequestSuccess() {
		LocalDateTime dateTime = LocalDateTime.now();
		Portfolio oldPortfolio = new Portfolio() //
				.withName("Test Portfolio") //
				.withDescription("Test Description").withBudgets(new ArrayList<>()) //
				.withCreated(dateTime) //
				;
		UpdatePortfolioRequest request = new UpdatePortfolioRequest() //
				.withName("new name") //
				.withDescription("new description");

		Portfolio portfolio = PortfolioConverter.convert(request, oldPortfolio);

		assertEquals(request.getName(), portfolio.getName());
		assertEquals(request.getDescription(), portfolio.getDescription());
		assertEquals(oldPortfolio.getBudgets(), portfolio.getBudgets());
		assertEquals(dateTime.getYear(), portfolio.getCreated().getYear());
		assertEquals(dateTime.getMonth(), portfolio.getCreated().getMonth());
		assertEquals(dateTime.getDayOfMonth(), portfolio.getCreated().getDayOfMonth());
		assertEquals(dateTime.getHour(), portfolio.getCreated().getHour());
		assertEquals(dateTime.getMinute(), portfolio.getCreated().getMinute());
		assertEquals(dateTime.getSecond(), portfolio.getCreated().getSecond());
	}

}

