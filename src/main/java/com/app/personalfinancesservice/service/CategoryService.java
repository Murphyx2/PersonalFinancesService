package com.app.personalfinancesservice.service;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryConverter;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.CreateCategoryRequest;
import com.app.personalfinancesservice.domain.category.output.CreateCategoryResponse;
import com.app.personalfinancesservice.domain.service.CategoryServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.repository.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceBase {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public CreateCategoryResponse createCategory(CreateCategoryRequest request) {

		Category result;

		//Check if there is a category with the same name transaction type
		//TODO: Add Missing validation, Check by name and type if It already exists.

		if(categoryExists(request)){
			return new CreateCategoryResponse();
		}

		try {
			result = categoryRepository.save(CategoryConverter.convert(request));
		} catch (Exception e) {
			throw new CreateNewItemException("CREATE_NEW", "", CATEGORY_LABEL);
		}

		return new CreateCategoryResponse().withCategory(result);
	}

	private boolean categoryExists(CreateCategoryRequest request) {
		//TODO: To be implemented.
		return false;
	}
}
