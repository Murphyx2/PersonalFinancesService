package com.app.personalfinancesservice.domain.categoryplanner.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteCategoryPlannerRequest {

	private String id;
	private String userId;

	public DeleteCategoryPlannerRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public DeleteCategoryPlannerRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
