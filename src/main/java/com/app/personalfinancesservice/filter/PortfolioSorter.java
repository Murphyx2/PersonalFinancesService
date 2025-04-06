package com.app.personalfinancesservice.filter;

import java.util.Comparator;
import java.util.List;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;

public class PortfolioSorter {

	private static Comparator<Portfolio> getComparator(SortBy sortBy) {

		return switch (sortBy) {
			case NAME -> Comparator.comparing(Portfolio::getName);
			case CREATED_AT -> Comparator.comparing(Portfolio::getCreated);
			default -> Comparator.comparing(Portfolio::getId);
		};

	}

	public static List<Portfolio> sort(List<Portfolio> portfolios, SortBy sortBy, SortDirection sortDirection) {

		if (portfolios == null || portfolios.isEmpty()) {
			return portfolios;
		}

		Comparator<Portfolio> comparator = getComparator(sortBy);

		if (SortDirection.DESC.equals(sortDirection)) {
			comparator = comparator.reversed();
		}

		portfolios.sort(comparator);

		return portfolios;

	}

	private PortfolioSorter() {
		// Empty on purpose
	}
}
