package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;

public interface CategoryServiceBase {

	public CreateCategoryResponse createCategory(CreateCategoryRequest request);
}
