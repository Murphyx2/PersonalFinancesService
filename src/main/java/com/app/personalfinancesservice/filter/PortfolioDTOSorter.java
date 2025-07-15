package com.app.personalfinancesservice.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;
import com.personalfinance.api.filter.SortBy;
import com.personalfinance.api.filter.SortDirection;

public class PortfolioDTOSorter {

	private static Comparator<PortfolioDTO> getComparator(SortBy sortBy) {

		return switch (sortBy) {
			case NAME -> Comparator.comparing(PortfolioDTO::getName);
			case CREATED_AT -> Comparator.comparing(PortfolioDTO::getCreated);
			default -> Comparator.comparing(PortfolioDTO::getId);
		};

	}

	public static List<PortfolioDTO> sort(List<PortfolioDTO> portfolios, SortBy sortBy, SortDirection sortDirection) {

		if (portfolios == null || portfolios.isEmpty()) {
			return portfolios;
		}

		// Copy list to sort
		List<PortfolioDTO> portfoliosToSort = new ArrayList<>(portfolios);

		Comparator<PortfolioDTO> comparator = getComparator(sortBy);

		if (SortDirection.DESC.equals(sortDirection)) {
			comparator = comparator.reversed();
		}

		portfoliosToSort.sort(comparator);

		return portfoliosToSort;

	}

	private PortfolioDTOSorter() {
		// Empty on purpose
	}
}
