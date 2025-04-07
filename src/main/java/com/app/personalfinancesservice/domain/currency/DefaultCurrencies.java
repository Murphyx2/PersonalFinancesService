package com.app.personalfinancesservice.domain.currency;

import java.util.List;
import java.util.stream.Stream;

public enum DefaultCurrencies {

	USD, CAD, DOP, EUR;

	public static List<String> getCurrenciesCodes() {
		return Stream.of(values()) //
				.map(DefaultCurrencies::name) //
				.toList();
	}
}
