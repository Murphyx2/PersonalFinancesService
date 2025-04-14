package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.currency.input.GetCurrencyRequest;
import com.app.personalfinancesservice.domain.currency.output.GetCurrencyResponse;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.service.CurrencyService;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.CURRENCY)
public class CurrencyController {

	private final CurrencyService currencyService;

	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping
	public ResponseEntity<GetCurrencyResponse> getCurrencies( //
			@RequestParam(required = false, defaultValue = "true") boolean defaultCurrencies, //
			@RequestParam(required = false) String startWith) {

		GetCurrencyRequest request = new GetCurrencyRequest() //
				.withStartWith(startWith) //
				.withShowDefault(defaultCurrencies);

		return ResponseEntity.ok(currencyService.getCurrencies(request));
	}
}
