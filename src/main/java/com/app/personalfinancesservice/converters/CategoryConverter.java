package com.app.personalfinancesservice.converters;

import java.time.LocalDateTime;
import java.util.UUID;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.category.dto.CategoryDTO;
import com.personalfinance.api.domain.category.input.CreateCategoryRequest;

public class CategoryConverter {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private static final String USER_ID_LABEL = "userId";

	public static Category convert(CreateCategoryRequest request) {

		if (request == null) {
			return null;
		}

		UUID userId = UUIDConverter.convert(request.getUserId(), USER_ID_LABEL, CATEGORY_LABEL);

		return new Category() //
				.withName(request.getName().toUpperCase()) //
				.withUserId(userId) //
				.withTransactionType(request.getTransactionType()) //
				.withCreatedAt(LocalDateTime.now());
	}

	public static Category convert(Category oldCategory, CategoryDTO request) {
		if (oldCategory == null || request == null) {
			return null;
		}

		return oldCategory //
				.withName(request.getName().toUpperCase()) //
				.withTransactionType(request.getTransactionType());

	}

	public static Category convert(CategoryDTO categoryDTO) {

		if (categoryDTO == null) {
			return null;
		}

		return new Category() //
				.withId(UUIDConverter.convert(categoryDTO.getId(), "id", CATEGORY_LABEL)) //
				.withUserId(UUIDConverter.convert(categoryDTO.getUserId(), USER_ID_LABEL, CATEGORY_LABEL)) //
				.withName(categoryDTO.getName()) //
				.withTransactionType(categoryDTO.getTransactionType()) //
				.withCreatedAt(categoryDTO.getCreatedAt()) //
				;
	}

	private CategoryConverter() {
		// Empty on Purpose
	}
}
