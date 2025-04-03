package com.app.personalfinancesservice.domain.portfolio.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePortfolioRequest {

	private String id;
	private String userId;
	private String name;
	private String description;

	public UpdatePortfolioRequest withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public UpdatePortfolioRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public UpdatePortfolioRequest withName(String name) {
		this.setName(name);
		return this;
	}

	public UpdatePortfolioRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
