package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;

public class CategoryPlannerSorter {

	public static List<CategoryPlanner> sort(final List<CategoryPlanner> categoryPlanners, SortBy sortBy, SortDirection direction) {

		if (categoryPlanners == null || categoryPlanners.isEmpty()) {
			return categoryPlanners;
		}

		Comparator<CategoryPlanner> comparator = getComparator(sortBy);
		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		List<CategoryPlanner> sortedCategoryPlanners = new ArrayList<>(categoryPlanners);
		sortedCategoryPlanners.sort(comparator);
		return sortedCategoryPlanners;
	}

	private static Comparator<CategoryPlanner> getComparator(SortBy sortBy) {

		if (sortBy == null) {
			sortBy = SortBy.CREATED_AT;
		}

		return switch (sortBy) {
			case SortBy.NAME -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getName());
			case SortBy.TRANSACTION_TYPE -> Comparator.comparing(categoryPlanner -> categoryPlanner.getCategory().getTransactionType());
			default -> Comparator.comparing(CategoryPlanner::getCreatedAt);

		};
	}

	private CategoryPlannerSorter() {
		// Empty on purpose
	}
}
