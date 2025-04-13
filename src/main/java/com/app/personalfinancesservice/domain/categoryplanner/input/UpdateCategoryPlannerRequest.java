package com.app.personalfinancesservice.domain.categoryplanner.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryPlannerRequest {

	private String id;
	private String userId;
	private String categoryId;
	private Double plannedAmount;

	public UpdateCategoryPlannerRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public UpdateCategoryPlannerRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}

	public UpdateCategoryPlannerRequest withCategoryId(String categoryId) {
		this.setCategoryId(categoryId);
		return this;
	}

	public UpdateCategoryPlannerRequest withPlannedAmount(Double plannedAmount) {
		this.setPlannedAmount(plannedAmount);
		return this;
	}
}
