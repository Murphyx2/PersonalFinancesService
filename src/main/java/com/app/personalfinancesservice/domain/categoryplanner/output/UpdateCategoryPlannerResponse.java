package com.app.personalfinancesservice.domain.categoryplanner.output;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryPlannerResponse {

	private CategoryPlanner categoryPlanner;

	public UpdateCategoryPlannerResponse withCategoryPlanner(CategoryPlanner categoryPlanner) {
		this.setCategoryPlanner(categoryPlanner);
		return this;
	}
}
