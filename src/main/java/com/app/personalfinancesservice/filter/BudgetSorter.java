package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.personalfinance.api.domain.budget.dto.BudgetDTO;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

public class BudgetSorter {

	public static List<BudgetDTO> sort(final List<BudgetDTO> budgets, SortBy sortBy, SortDirection direction) {

		if (budgets == null || budgets.isEmpty()) {
			return budgets;
		}

		Comparator<BudgetDTO> comparator = getComparator(sortBy);

		if (SortDirection.DESC.equals(direction)) {
			comparator = comparator.reversed();
		}

		List<BudgetDTO> sorterBudgets = new ArrayList<>(budgets);
		sorterBudgets.sort(comparator);
		return sorterBudgets;
	}

	private static Comparator<BudgetDTO> getComparator(SortBy sortBy) {

		if (sortBy == null) {
			sortBy = SortBy.CREATED_AT;
		}

		return switch (sortBy) {
			case SortBy.NAME -> Comparator.comparing(BudgetDTO::getName);
			case SortBy.START_AT -> Comparator.comparing(BudgetDTO::getStartAt);
			case SortBy.END_AT -> Comparator.comparing(BudgetDTO::getEndAt);
			default -> Comparator.comparing(BudgetDTO::getCreatedAt);
		};
	}

	private BudgetSorter() {
		// Empty on Purpose
	}

}
