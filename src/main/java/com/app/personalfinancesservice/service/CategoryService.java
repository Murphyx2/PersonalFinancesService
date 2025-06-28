package com.app.personalfinancesservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryConverter;
import com.app.personalfinancesservice.converters.CategoryDTOConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.facade.category.CategoryRepositoryFacade;
import com.app.personalfinancesservice.filter.CategoryFilter;
import com.app.personalfinancesservice.filter.CategorySorter;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.category.input.CreateCategoryRequest;
import com.personalfinance.api.domain.category.input.DeleteCategoryRequest;
import com.personalfinance.api.domain.category.input.GetCategoriesRequest;
import com.personalfinance.api.domain.category.input.GetCategoryRequest;
import com.personalfinance.api.domain.category.input.UpdateCategoryRequest;
import com.personalfinance.api.domain.category.output.CreateCategoryResponse;
import com.personalfinance.api.domain.category.output.DeleteCategoryResponse;
import com.personalfinance.api.domain.category.output.GetCategoriesResponse;
import com.personalfinance.api.domain.category.output.GetCategoryResponse;
import com.personalfinance.api.domain.category.output.UpdateCategoryResponse;
import com.personalfinance.api.service.CategoryServiceBase;

@Service
public class CategoryService implements CategoryServiceBase {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private final CategoryRepositoryFacade categoryRepositoryFacade;

	public CategoryService(CategoryRepositoryFacade categoryRepositoryFacade) {
		this.categoryRepositoryFacade = categoryRepositoryFacade;
	}

	@Override
	public CreateCategoryResponse createCategory(CreateCategoryRequest request) {

		// Check if there is a category with the same name and Transaction Type
		if (categoryRepositoryFacade //
				.categoryExists( //
						request.getUserId(), //
						request.getName(),  //
						request.getTransactionType() //
				) //
		) {
			throw new CreateNewItemException("category", //
					String.format("Category: %s already exists", request.getName()) //
					, CATEGORY_LABEL);
		}

		Category category = categoryRepositoryFacade //
				.saveCategory(CategoryConverter.convert(request));

		return new CreateCategoryResponse() //
				.withCategory(CategoryDTOConverter.convert(category));
	}

	@Override
	public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest request) {

		Category category = categoryRepositoryFacade //
				.getCategory(request.getId(), request.getUserId());
		if (category == null) {
			return new DeleteCategoryResponse().withSuccess(true);
		}

		return new DeleteCategoryResponse() //
				.withSuccess(categoryRepositoryFacade.deleteCategory(category));
	}

	@Override
	public GetCategoriesResponse getCategories(GetCategoriesRequest request) {

		List<Category> categories = categoryRepositoryFacade //
				.getCategories(request.getUserId());
		// Filter results
		List<Category> filteredCategories = CategoryFilter //
				.filterByTransactionType(categories, request.getTransactionType());
		// Sort the results
		List<Category> categorySorter = CategorySorter //
				.sort(filteredCategories, request.getSortBy(), request.getSortDirection());

		return new GetCategoriesResponse() //
				.withCategories(CategoryDTOConverter.convertMany(categorySorter));
	}

	@Override
	public GetCategoryResponse getCategory(GetCategoryRequest request) {

		Category category = categoryRepositoryFacade //
				.getCategory(request.getId(), request.getUserId());

		return new GetCategoryResponse() //
				.withCategory(CategoryDTOConverter.convert(category));
	}

	@Override
	public UpdateCategoryResponse updateCategory(UpdateCategoryRequest request) {

		Category oldCategory = categoryRepositoryFacade //
				.getCategory(request.getId(), request.getUserId());
		if (oldCategory == null) {
			throw new NotFoundException(CATEGORY_LABEL, "category", request.getId());
		}

		Category updatedCategory = categoryRepositoryFacade //
				.saveCategory(CategoryConverter.convert(oldCategory, request));

		return new UpdateCategoryResponse() //
				.withCategory(CategoryDTOConverter.convert(updatedCategory));
	}
}
