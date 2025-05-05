package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.DeleteCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoriesRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.UpdateCategoryRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.DeleteCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoriesResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.UpdateCategoryResponse;

public interface CategoryServiceBase {

	public CreateCategoryResponse createCategory(CreateCategoryRequest request);

	public GetCategoriesResponse getCategories(GetCategoriesRequest request);

	public GetCategoryResponse getCategory(GetCategoryRequest request);

	public UpdateCategoryResponse updateCategory(UpdateCategoryRequest request);

	public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest request);
}
