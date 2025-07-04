package com.app.personalfinancesservice.filter;

import java.util.List;

import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.domain.transaction.TransactionType;

public class CategoryPlannerFilter {

	public static List<CategoryPlanner> filterByTransactionType(List<CategoryPlanner> categoryPlanners, TransactionType transactionType) {

		if (categoryPlanners == null || categoryPlanners.isEmpty() || transactionType == null) {
			return categoryPlanners;
		}

		return categoryPlanners.stream().filter(categoryPlanner -> categoryPlanner //
				.getCategory() //
				.getTransactionType() //
				.equals(transactionType)).toList();

	}

	public static List<CategoryPlanner> filterByName(List<CategoryPlanner> categoryPlanners, String name) {

		if (categoryPlanners == null || categoryPlanners.isEmpty() || name == null || name.isEmpty()) {
			return categoryPlanners;
		}

		return categoryPlanners.stream().filter(categoryPlanner -> categoryPlanner //
				.getCategory() //
				.getName() //
				.contains(name)).toList();
	}

	private CategoryPlannerFilter() {
		// Empty on purpose
	}
}
