package com.app.personalfinancesservice.domain.currency.output;

import java.util.Currency;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCurrencyResponse {

	private Set<Currency> currency;

	public GetCurrencyResponse withCurrency(Set<Currency> currencies) {
		this.setCurrency(currencies);
		return this;
	}
}
