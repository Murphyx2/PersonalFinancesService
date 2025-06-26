package com.app.personalfinancesservice.converters;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.category.dto.CategoryDTO;

public class CategoryDTOConverter {

	public static CategoryDTO convert(Category category) {
		return new CategoryDTO() //
				.withId(category.getId().toString()) //
				.withUserId(category.getUserId().toString()) //
				.withName(category.getName()) //
				.withTransactionType(category.getTransactionType()) //
				.withCreatedAt(category.getCreatedAt()) //
		;
	}

	private CategoryDTOConverter() {
		// Empty on purpose
	}
}
