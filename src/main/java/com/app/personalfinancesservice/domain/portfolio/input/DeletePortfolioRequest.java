package com.app.personalfinancesservice.domain.portfolio.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePortfolioRequest {

	private String id;
	private String userId;

	public DeletePortfolioRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public DeletePortfolioRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
