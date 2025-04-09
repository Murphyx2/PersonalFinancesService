package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoryResponse;

public interface CategoryServiceBase {

	public CreateCategoryResponse createCategory(CreateCategoryRequest request);

	public GetCategoryResponse getCategory(GetCategoryRequest request);
}
