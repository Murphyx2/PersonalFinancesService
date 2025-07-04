package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

public class BudgetSorter {

	public static List<Budget> sort(final List<Budget> budgets, SortBy sortBy, SortDirection direction) {

		if (budgets == null || budgets.isEmpty()) {
			return budgets;
		}

		Comparator<Budget> comparator = getComparator(sortBy);

		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		List<Budget> sorterBudgets = new ArrayList<>(budgets);
		sorterBudgets.sort(comparator);
		return sorterBudgets;
	}

	private static Comparator<Budget> getComparator(SortBy sortBy) {

		if(sortBy == null){
			sortBy = SortBy.CREATED_AT;
		}

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
