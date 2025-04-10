package com.app.personalfinancesservice.domain.category.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteCategoryResponse {

	private boolean success;

	public DeleteCategoryResponse withSuccess(boolean success) {
		this.setSuccess(success);
		return this;
	}
}
