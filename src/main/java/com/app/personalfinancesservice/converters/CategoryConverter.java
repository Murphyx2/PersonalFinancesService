package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.UpdateCategoryRequest;

public class CategoryConverter {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private static final String USER_ID_LABEL = "userId";

	public static Category convert(CreateCategoryRequest request) {

		UUID userId = UUIDConverter.convert(request.getUserId(), USER_ID_LABEL, CATEGORY_LABEL);

		return new Category() //
				.withName(request.getName().toUpperCase()) //
				.withUserId(userId) //
				.withTransactionType(request.getTransactionType()) //
				.withCreatedAt(LocalDateTime.now());
	}

	public static Category convert(Category oldCategory, UpdateCategoryRequest request) {

		return oldCategory //
				.withName(request.getName().toUpperCase()) //
				.withTransactionType(request.getTransactionType());

	}

	private CategoryConverter() {
		// Empty on Purpose
	}
}
