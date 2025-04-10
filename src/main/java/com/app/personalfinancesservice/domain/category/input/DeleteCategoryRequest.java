package com.app.personalfinancesservice.domain.category.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteCategoryRequest {

	private String id;

	private String userId;

	public DeleteCategoryRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public DeleteCategoryRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
