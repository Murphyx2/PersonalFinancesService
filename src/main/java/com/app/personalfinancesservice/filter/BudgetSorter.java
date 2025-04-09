package com.app.personalfinancesservice.filter;

import java.util.Comparator;
import java.util.List;

import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;

public class BudgetSorter {

	public static List<Budget> sort(final List<Budget> budgets, SortBy sortBy, SortDirection direction) {

		if (budgets == null || budgets.isEmpty()) {
			return budgets;
		}

		Comparator<Budget> comparator = getComparator(sortBy);

		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		budgets.sort(comparator);
		return budgets;
	}

	private static Comparator<Budget> getComparator(SortBy sortBy) {

		return switch (sortBy) {
			case SortBy.NAME -> Comparator.comparing(Budget::getName);
			case SortBy.START_AT -> Comparator.comparing(Budget::getStartAt);
			case SortBy.END_AT -> Comparator.comparing(Budget::getEndAt);
			default -> Comparator.comparing(Budget::getCreatedAt);
		};
	}

	private BudgetSorter() {
		// Empty on Purpose
	}

}
