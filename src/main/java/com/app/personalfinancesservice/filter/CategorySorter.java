package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;

public class CategorySorter {

	public static List<Category> sort(List<Category> categories, SortBy sortBy, SortDirection direction) {

		if (categories == null || categories.isEmpty()) {
			return categories;
		}

		Comparator<Category> comparator = getComparator(sortBy);
		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		// Clone List
		List<Category> sortedCategories = new ArrayList<>(categories);
		// Apply sort
		sortedCategories.sort(comparator);
		return sortedCategories;
	}

	private static Comparator<Category> getComparator(SortBy sortBy) {

		return switch (sortBy) {
			case SortBy.NAME -> Comparator.comparing(Category::getName);
			default -> Comparator.comparing(Category::getCreatedAt);
		};
	}

	private CategorySorter() {
		// Empty on Purpose
	}

}
