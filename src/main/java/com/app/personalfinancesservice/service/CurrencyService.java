package com.app.personalfinancesservice.service;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.domain.currency.DefaultCurrencies;
import com.app.personalfinancesservice.domain.currency.input.GetCurrencyRequest;
import com.app.personalfinancesservice.domain.currency.output.GetCurrencyResponse;
import com.app.personalfinancesservice.domain.service.CurrencyServiceBase;

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

		return new GetCurrencyResponse().withCurrency(currencies);
	}
}
