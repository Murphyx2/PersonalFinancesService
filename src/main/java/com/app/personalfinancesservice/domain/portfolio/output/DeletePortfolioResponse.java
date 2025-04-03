package com.app.personalfinancesservice.domain.portfolio.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePortfolioResponse {

	private boolean success;

	public DeletePortfolioResponse withSuccess(boolean success) {
		this.setSuccess(success);
		return this;
	}
}
