package com.app.personalfinancesservice.converters;

import java.util.ArrayList;
import java.util.List;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.category.dto.CategoryDTO;

public class CategoryDTOConverter {

	public static CategoryDTO convert(Category category) {

		if (category == null) {
			return null;
		}

		return new CategoryDTO() //
				.withId(category.getId().toString()) //
				.withUserId(category.getUserId().toString()) //
				.withName(category.getName()) //
				.withTransactionType(category.getTransactionType()) //
				.withCreatedAt(category.getCreatedAt()) //
				;
	}

	public static List<CategoryDTO> convertMany(List<Category> categories){
		if (categories == null || categories.isEmpty()) {
			return new ArrayList<>();
		}

		return categories //
				.stream() //
				.map(CategoryDTOConverter::convert) //
				.toList();
	}

	private CategoryDTOConverter() {
		// Empty on purpose
	}
}
