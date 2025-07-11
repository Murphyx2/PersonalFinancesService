package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.personalfinance.api.domain.category.dto.CategoryDTO;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

public class CategorySorter {

	public static List<CategoryDTO> sort(List<CategoryDTO> categories, SortBy sortBy, SortDirection direction) {

		if (categories == null || categories.isEmpty()) {
			return categories;
		}

		Comparator<CategoryDTO> comparator = getComparator(sortBy);
		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		// Clone List
		List<CategoryDTO> sortedCategories = new ArrayList<>(categories);
		// Apply sort
		sortedCategories.sort(comparator);
		return sortedCategories;
	}

	private static Comparator<CategoryDTO> getComparator(SortBy sortBy) {

		if (sortBy == null) {
			sortBy = SortBy.CREATED_AT;
		}

		return switch (sortBy) {
			case SortBy.NAME -> Comparator.comparing(CategoryDTO::getName);
			default -> Comparator.comparing(CategoryDTO::getCreatedAt);
		};
	}

	private CategorySorter() {
		// Empty on Purpose
	}

}
