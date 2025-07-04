package com.app.personalfinancesservice.converter;

import java.util.UUID;

import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.converters.CategoryConverter;
import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.domain.budget.input.CreateBudgetRequest;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.category.input.UpdateCategoryRequest;
import com.personalfinance.api.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BudgetConverterTest {

	@Test
	void convertCreateBudgetRequestSuccess() {

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

	@Test
	void convertUpdateBudgetRequestSuccess() {

		UUID userId = UUID.randomUUID();
		UUID id = UUID.randomUUID();

		UpdateCategoryRequest request = new UpdateCategoryRequest() //
				.withId(id.toString()) //
				.withUserId(userId.toString()) //
				.withName("Updated Name") //
				.withTransactionType(TransactionType.INCOME);

		Category oldCategory = new Category() //
				.withId(id) //
				.withUserId(userId) //
				.withName("Old Name") //
				.withTransactionType(TransactionType.EXPENSE);

		Category updateCategory = CategoryConverter.convert(oldCategory, request);

		assertEquals(updateCategory.getId(), id);
		assertEquals(updateCategory.getUserId(), userId);
		assertEquals(updateCategory.getName(), request.getName().toUpperCase());
		assertEquals(updateCategory.getTransactionType(), request.getTransactionType());
	}
}
