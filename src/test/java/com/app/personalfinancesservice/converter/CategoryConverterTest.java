package com.app.personalfinancesservice.converter;

import java.util.UUID;

import com.app.personalfinancesservice.converters.CategoryConverter;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CategoryConverterTest {

	@Test
	void convertSuccessTest() {
		CreateCategoryRequest request = new CreateCategoryRequest() //
				.withName("test") //
				.withUserId(UUID.randomUUID().toString()) //
				.withTransactionType(TransactionType.EXPENSE);

		Category convertedCategory = CategoryConverter.convert(request);

		assertEquals(request.getName().toUpperCase(), convertedCategory.getName());
		assertEquals(request.getUserId(), convertedCategory.getUserId().toString());
		assertEquals(request.getTransactionType(), convertedCategory.getTransactionType());
		assertNotNull(convertedCategory.getCreatedAt());
	}
}
