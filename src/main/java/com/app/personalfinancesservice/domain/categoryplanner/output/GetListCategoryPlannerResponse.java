package com.app.personalfinancesservice.domain.categoryplanner.output;

import java.util.List;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetListCategoryPlannerResponse {

	private List<CategoryPlanner> categoryPlanners;

	public GetListCategoryPlannerResponse withCategoryPlanners(List<CategoryPlanner> categoryPlanners) {
		this.setCategoryPlanners(categoryPlanners);
		return this;
	}
}
