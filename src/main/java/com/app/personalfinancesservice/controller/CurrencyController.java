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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.CURRENCY)
@Tag(name = "Currency Management", description = "Endpoint to manage currency types or conversions")
public class CurrencyController {

	private final CurrencyService currencyService;

	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping
	@Operation(summary = "Retrieve a list of currencies", description = "Retrieve a list of currencies codes")
	public ResponseEntity<GetCurrencyResponse> getCurrencies( //
			@Parameter(description = "Boolean field to retrieve default currencies") //
			@RequestParam(required = false, defaultValue = "true") boolean defaultCurrencies, //
			@Parameter(description = "Filter currencies by string") //
			@RequestParam(required = false) String startWith) {

		GetCurrencyRequest request = new GetCurrencyRequest() //
				.withStartWith(startWith) //
				.withShowDefault(defaultCurrencies);

		return ResponseEntity.ok(currencyService.getCurrencies(request));
	}
}
