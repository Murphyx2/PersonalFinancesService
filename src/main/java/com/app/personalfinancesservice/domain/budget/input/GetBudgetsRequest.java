package com.app.personalfinancesservice.domain.budget.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBudgetsRequest {

	private String id;
	private String userId;

	public GetBudgetsRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public GetBudgetsRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
