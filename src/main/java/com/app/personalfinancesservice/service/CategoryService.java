package com.app.personalfinancesservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.DeleteCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.category.input.UpdateCategoryRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.DeleteCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.GetCategoryResponse;
import com.app.personalfinancesservice.domain.category.output.UpdateCategoryResponse;
import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.service.CategoryServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.CategoryFilter;
import com.app.personalfinancesservice.filter.CategorySorter;
import com.app.personalfinancesservice.repository.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceBase {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	private boolean categoryExists(CreateCategoryRequest request) {

		GetCategoryRequest getRequest = new GetCategoryRequest() //
				.withUserId(request.getUserId()) //
				.withTransactionType(request.getTransactionType()) //
				.withSortBy(SortBy.NAME);

		List<Category> categories = getCategory(getRequest).getCategory();

		if (categories.isEmpty()) {
			return false;
		}

		return categories.stream() //
				.anyMatch(category -> category.getName().equalsIgnoreCase(request.getName()) //
				);
	}

	public boolean categoryExists(UUID categoryId, UUID userId) {
		return categoryRepository.existsByIdAndUserId(categoryId, userId);
	}

	@Override
	public CreateCategoryResponse createCategory(CreateCategoryRequest request) {

		Category result;

		if (categoryExists(request)) {
			throw new CreateNewItemException("category", //
					String.format("Category: %s already exists", request.getName()) //
					, CATEGORY_LABEL);
		}

		try {
			result = categoryRepository.save(CategoryConverter.convert(request));
		} catch (Exception e) {
			throw new CreateNewItemException("new category",  //
					String.format("Could not save %s", request.getName()) //
					, CATEGORY_LABEL);
		}

		return new CreateCategoryResponse().withCategory(result);
	}

	@Override
	public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest request) {

		GetCategoryRequest getRequest = new GetCategoryRequest() //
				.withId(request.getId()) //
				.withUserId(request.getUserId()) //
				;

		List<Category> category = getCategory(getRequest).getCategory();
		if (category.isEmpty()) {
			return new DeleteCategoryResponse().withSuccess(true);
		}

		categoryRepository.delete(category.getFirst());

		return new DeleteCategoryResponse().withSuccess(true);
	}

	@Override
	public GetCategoryResponse getCategory(GetCategoryRequest request) {

		List<Category> result;
		UUID userId = UUIDConverter.convert(request.getUserId(), "userId", CATEGORY_LABEL);

		if (request.getId() == null || request.getId().isEmpty()) {
			List<Category> categories = categoryRepository.getCategoriesByUserId(userId);
			// Filter results
			List<Category> filteredCategories = CategoryFilter.filterByTransactionType(categories, request.getTransactionType());
			// Sort the results
			result = CategorySorter.sort(filteredCategories, request.getSortBy(), request.getSortDirection());
		} else {
			UUID categoryId = UUIDConverter.convert(request.getId(), "categoryId", CATEGORY_LABEL);
			result = categoryRepository.getCategoriesByIdAndUserId(categoryId, userId);
		}

		return new GetCategoryResponse().withCategory(result);
	}

	@Override
	public UpdateCategoryResponse updateCategory(UpdateCategoryRequest request) {

		GetCategoryRequest getRequest = new GetCategoryRequest() //
				.withId(request.getId()) //
				.withUserId(request.getUserId());
		List<Category> oldCategory = getCategory(getRequest).getCategory();
		if (oldCategory.isEmpty()) {
			throw new NotFoundException(CATEGORY_LABEL, "category", request.getId());
		}

		Category result = categoryRepository.save(CategoryConverter.convert(oldCategory.getFirst(), request));

		return new UpdateCategoryResponse().withCategory(result);
	}
}
