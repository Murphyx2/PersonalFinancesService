package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

public class CategoryPlannerSorter {

	public static List<CategoryPlannerDTO> sort(final List<CategoryPlannerDTO> categoryPlanners, SortBy sortBy, SortDirection direction) {

		if (categoryPlanners == null || categoryPlanners.isEmpty()) {
			return categoryPlanners;
		}

		Comparator<CategoryPlannerDTO> comparator = getComparator(sortBy);
		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		List<CategoryPlannerDTO> sortedCategoryPlanners = new ArrayList<>(categoryPlanners);
		sortedCategoryPlanners.sort(comparator);
		return sortedCategoryPlanners;
	}

	private static Comparator<CategoryPlannerDTO> getComparator(SortBy sortBy) {

		if (sortBy == null) {
			sortBy = SortBy.CREATED_AT;
		}

		return switch (sortBy) {
			case SortBy.NAME -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getName());
			case SortBy.TRANSACTION_TYPE -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getTransactionType());
			default -> Comparator.comparing(CategoryPlannerDTO::getCreatedAt);

		};
	}

	private CategoryPlannerSorter() {
		// Empty on purpose
	}
}
