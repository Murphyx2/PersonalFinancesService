package com.app.personalfinancesservice.service;

import java.util.Comparator;
import java.util.Currency;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.personalfinance.api.domain.currency.DefaultCurrencies;
import com.personalfinance.api.domain.currency.input.GetCurrencyRequest;
import com.personalfinance.api.domain.currency.output.GetCurrencyResponse;
import com.personalfinance.api.service.CurrencyServiceBase;

@Service
public class CurrencyService implements CurrencyServiceBase {

	@Override
	public GetCurrencyResponse getCurrencies(GetCurrencyRequest request) {

		Set<Currency> currencies = Currency.getAvailableCurrencies();

		if (request.getStartWith() != null && !request.getStartWith().isBlank()) {
			currencies = currencies.stream().filter(currency -> currency //
							.getCurrencyCode() //
							.startsWith(request.getStartWith())) //
					.collect(Collectors.toSet());
		} else if (request.isShowDefault()) {
			currencies = currencies //
					.stream().filter(currency -> DefaultCurrencies.getCurrenciesCodes() //
							.contains(currency.getCurrencyCode())) //
					.collect(Collectors.toSet());
		}

		// Apply sorting
		LinkedHashSet<Currency> currencySet = currencies //
				.stream() //
				.sorted(Comparator.comparing(Currency::getCurrencyCode)) //
				.collect(Collectors.toCollection(LinkedHashSet::new));

		return new GetCurrencyResponse().withCurrency(currencySet);
	}
}
