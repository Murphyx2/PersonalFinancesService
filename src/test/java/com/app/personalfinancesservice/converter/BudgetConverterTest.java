package com.app.personalfinancesservice.converter;

import java.util.UUID;

import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BudgetConverterTest {

	@Test
	void convertCreateBudgetRequestSuccess(){

		CreateBudgetRequest request = new CreateBudgetRequest() //
				.withUserId(UUID.randomUUID().toString()) //
				.withPortfolioId(UUID.randomUUID().toString()) //
				.withName("Test Name") //
				.withDescription("Test Description");

		Budget budget = BudgetConverter.convert(request);

		assertEquals(request.getPortfolioId(), budget.getPortfolioId().toString());
		assertEquals(request.getUserId(), budget.getUserId().toString());
		assertEquals(request.getName(), budget.getName());
		assertNotNull(budget.getCreatedAt());
	}

}
