package com.app.personalfinancesservice.domain.currency.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCurrencyRequest {

	private boolean showDefault;
	private String startWith;

	public GetCurrencyRequest withShowDefault(boolean showDefault) {
		this.setShowDefault(showDefault);
		return this;
	}

	public GetCurrencyRequest withStartWith(String startWith) {
		this.setStartWith(startWith);
		return this;
	}
}
