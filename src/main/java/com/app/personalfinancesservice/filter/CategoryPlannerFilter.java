package com.app.personalfinancesservice.filter;

import java.util.List;

import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.domain.transaction.TransactionType;

public class CategoryPlannerFilter {

	public static List<CategoryPlannerDTO> filterByTransactionType(List<CategoryPlannerDTO> categoryPlanners, TransactionType transactionType) {

		if (categoryPlanners == null || categoryPlanners.isEmpty() || transactionType == null) {
			return categoryPlanners;
		}

		return categoryPlanners.stream().filter(categoryPlanner -> categoryPlanner //
				.getCategory() //
				.getTransactionType() //
				.equals(transactionType)).toList();

	}

	public static List<CategoryPlannerDTO> filterByName(List<CategoryPlannerDTO> categoryPlanners, String name) {

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
