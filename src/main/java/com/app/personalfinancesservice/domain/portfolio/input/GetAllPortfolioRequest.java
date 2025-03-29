package com.app.personalfinancesservice.domain.portfolio.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPortfolioRequest {

	private String userId;

	public GetAllPortfolioRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
