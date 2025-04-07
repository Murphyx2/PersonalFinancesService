package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;

public class CategoryConverter {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private static final String USER_ID_LABEL = "userId";

	public static Category convert(CreateCategoryRequest request) {

		UUID userId = UUIDConverter.convert(request.getUserId(), USER_ID_LABEL, CATEGORY_LABEL);

		return new Category() //
				.withName(request.getName()) //
				.withUserId(userId) //
				.withTransactionType(request.getTransactionType()) //
				.withCreatedAt(LocalDateTime.now());
	}

	private CategoryConverter() {
		// Empty on Purpose
	}
}
