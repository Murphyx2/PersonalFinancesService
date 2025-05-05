package com.app.personalfinancesservice.domain.category.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoryRequest {

	private String id;
	private String userId;

	public GetCategoryRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public GetCategoryRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
