package com.app.personalfinancesservice.domain.categoryplanner.output;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryPlannerResponse {

	private CategoryPlanner categoryPlanner;

	public CreateCategoryPlannerResponse withCategoryPlanner(CategoryPlanner categoryPlanner) {
		this.setCategoryPlanner(categoryPlanner);
		return this;
	}
}
