package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.UpdateCategoryPlannerResponse;

public interface CategoryPlannerServiceBase {

	public CreateCategoryPlannerResponse createCategoryPlanner(CreateCategoryPlannerRequest request);

	public void deleteCategory(DeleteCategoryPlannerRequest request);

	public GetCategoryPlannerResponse getCategory(GetCategoryPlannerRequest request);

	public UpdateCategoryPlannerResponse updateCategory(UpdateCategoryPlannerRequest request);
}
