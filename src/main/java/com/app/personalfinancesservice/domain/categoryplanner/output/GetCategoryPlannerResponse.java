package com.app.personalfinancesservice.domain.categoryplanner.output;

import java.util.List;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCategoryPlannerResponse {

	private List<CategoryPlanner> categoryPlanner;

	public GetCategoryPlannerResponse withCategoryPlanner(List<CategoryPlanner> categoryPlanner) {
		this.setCategoryPlanner(categoryPlanner);
		return this;
	}
}
