package com.app.personalfinancesservice.filter;

import java.util.List;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.transaction.TransactionType;

public class CategoryFilter {

	public static List<Category> filterByTransactionType(List<Category> categoryList, TransactionType transactionType) {

		if (categoryList == null || categoryList.isEmpty() || transactionType == null) {
			return categoryList;
		}

		return categoryList.stream() //
				.filter(category -> category.getTransactionType().equals(transactionType)) //
				.toList();
	}

	private CategoryFilter() {
		// Empty on purpose
	}
}
