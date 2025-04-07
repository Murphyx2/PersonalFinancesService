package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.currency.input.GetCurrencyRequest;
import com.app.personalfinancesservice.domain.currency.output.GetCurrencyResponse;

public interface CurrencyServiceBase {

	GetCurrencyResponse getCurrencies(GetCurrencyRequest request);
}
