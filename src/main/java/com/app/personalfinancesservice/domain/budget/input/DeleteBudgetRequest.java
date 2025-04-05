package com.app.personalfinancesservice.domain.budget.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBudgetRequest {

	private String id;
	private String userId;

	public DeleteBudgetRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public DeleteBudgetRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
