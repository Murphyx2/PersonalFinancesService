package com.app.personalfinancesservice.domain.budget.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBudgetResponse {

	private boolean success;

	public DeleteBudgetResponse withSuccess(boolean success) {
		this.setSuccess(success);
		return this;
	}
}
