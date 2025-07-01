package com.app.personalfinancesservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryPlannerConverter;
import com.app.personalfinancesservice.converters.CategoryPlannerDTOConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.CategoryPlannerFilter;
import com.app.personalfinancesservice.filter.CategoryPlannerSorter;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.GetListCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.GetListCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.UpdateCategoryPlannerResponse;
import com.personalfinance.api.facade.BudgetRepositoryFacade;
import com.personalfinance.api.facade.CategoryPlannerRepositoryFacade;
import com.personalfinance.api.facade.CategoryRepositoryFacade;
import com.personalfinance.api.service.CategoryPlannerServiceBase;

@Service
public class CategoryPlannerService implements CategoryPlannerServiceBase {

	private static final String CATEGORY_PLANNER = "CATEGORY_PLANNER";
	private static final String BUDGET_ID_LABEL = "budgetId";
	private static final String CATEGORY_ID_LABEL = "categoryId";

	CategoryPlannerRepositoryFacade categoryPlannerRepositoryFacade;
	BudgetRepositoryFacade budgetRepositoryFacade;
	CategoryRepositoryFacade categoryRepositoryFacade;

	public CategoryPlannerService( //
			CategoryPlannerRepositoryFacade categoryPlannerRepositoryFacade, //
			BudgetRepositoryFacade budgetRepositoryFacade, //
			CategoryRepositoryFacade categoryRepositoryFacade) {
		this.categoryPlannerRepositoryFacade = categoryPlannerRepositoryFacade;
		this.budgetRepositoryFacade = budgetRepositoryFacade;
		this.categoryRepositoryFacade = categoryRepositoryFacade;
	}

	@Override
	public CreateCategoryPlannerResponse createCategoryPlanner(CreateCategoryPlannerRequest request) {

		// Check if the budget exists
		if (!budgetRepositoryFacade.budgetExists(request.getBudgetId(), request.getUserId())) {
			throw new NotFoundException(CATEGORY_PLANNER, BUDGET_ID_LABEL, request.getBudgetId());
		}

		Category category = categoryRepositoryFacade //
				.getCategory(request.getCategoryId(), request.getUserId());

		// Check if Category exists
		if (category == null) {
			throw new NotFoundException(CATEGORY_PLANNER, CATEGORY_ID_LABEL, request.getBudgetId());
		}

		// Check if CategoryPlanner already exists
		if (categoryPlannerRepositoryFacade //
				.categoryPlannerExists( //
						request.getCategoryId(), //
						request.getBudgetId(),  //
						request.getUserId() //
				)//
		) {
			String message = String.format("CategoryPlanner for category of name %s already exists", //
					category.getName());
			throw new CreateNewItemException(CATEGORY_PLANNER, message);
		}

		CategoryPlanner newCategoryPlanner = CategoryPlannerConverter //
				.convert(request) //
				.withCategory(category);

		CategoryPlannerDTO categoryPlannerDTO = CategoryPlannerDTOConverter //
				.convert(categoryPlannerRepositoryFacade //
						.saveCategoryPlanner(newCategoryPlanner) //
				);

		return new CreateCategoryPlannerResponse() //
				.withCategoryPlanner(categoryPlannerDTO);
	}

	@Override
	public void deleteCategoryPlanner(DeleteCategoryPlannerRequest request) {

		// Get Category Planner
		CategoryPlanner categoryPlanner = categoryPlannerRepositoryFacade //
				.getCategoryPlanner(request.getId(), request.getUserId());
		if (categoryPlanner == null) {
			String message = String.format("CategoryPlanner of id %s could not be found", request.getId());
			throw new NotFoundException(CATEGORY_PLANNER, message);
		}

		categoryPlannerRepositoryFacade.deleteCategoryPlanner(categoryPlanner);
	}

	@Override
	public GetCategoryPlannerResponse getCategoryPlanner(GetCategoryPlannerRequest request) {

		CategoryPlanner categoryPlanner = categoryPlannerRepositoryFacade //
				.getCategoryPlanner(request.getId(), request.getUserId());

		return new GetCategoryPlannerResponse() //
				.withCategoryPlanner( //
						CategoryPlannerDTOConverter.convert(categoryPlanner) //
				);
	}

	@Override
	public GetListCategoryPlannerResponse getListCategoryPlanner(GetListCategoryPlannerRequest request) {

		// Check budget
		if (budgetRepositoryFacade.budgetExists(request.getBudgetId(), request.getUserId())) {
			throw new NotFoundException(CATEGORY_PLANNER, BUDGET_ID_LABEL, request.getBudgetId());
		}

		List<CategoryPlanner> categoryPlanners = categoryPlannerRepositoryFacade //
				.getCategoriesPlanner(request.getBudgetId(), request.getUserId());

		if (categoryPlanners == null || categoryPlanners.isEmpty()) {
			throw new NotFoundException(CATEGORY_PLANNER, "categoryPlanner on budgetId", request.getBudgetId());
		}
		// Apply filter
		List<CategoryPlanner> filteredCategoryPlanners = CategoryPlannerFilter //
				.filterByTransactionType(categoryPlanners, request.getTransactionType());

		filteredCategoryPlanners = CategoryPlannerFilter //
				.filterByName(filteredCategoryPlanners, request.getCategoryName());

		// Apply sort
		List<CategoryPlanner> sortedCategoryPlanner = CategoryPlannerSorter //
				.sort(filteredCategoryPlanners, request.getSortBy(), request.getSortDirection());

		return new GetListCategoryPlannerResponse() //
				.withCategoryPlanners(CategoryPlannerDTOConverter.convertMany(sortedCategoryPlanner));
	}

	@Override
	public UpdateCategoryPlannerResponse updateCategoryPlanner(UpdateCategoryPlannerRequest request) {

		CategoryPlanner oldCategoryPlanner = categoryPlannerRepositoryFacade //
				.getCategoryPlanner(request.getId(), request.getUserId());
		// Check if CategoryPlanner exists
		if (oldCategoryPlanner == null) {
			String message = String.format("CategoryPlanner of id %s could not be found", request.getId());
			throw new NotFoundException(CATEGORY_PLANNER, message);
		}

		Category category = categoryRepositoryFacade //
				.getCategory(request.getCategoryId(), request.getUserId());
		// Check if the category exists
		if (category == null) {
			String message = String.format("Category of id %s could not be found", request.getCategoryId());
			throw new NotFoundException(CATEGORY_PLANNER, message);
		}
		// Convert
		CategoryPlanner updatedCategoryPlanner = CategoryPlannerConverter //
				.convert(request, category, oldCategoryPlanner);

		CategoryPlanner updateCategoryPlanner = categoryPlannerRepositoryFacade //
				.saveCategoryPlanner(updatedCategoryPlanner);

		// Update category planner
		return new UpdateCategoryPlannerResponse() //
				.withCategoryPlanner(CategoryPlannerDTOConverter.convert(updateCategoryPlanner));
	}
}
