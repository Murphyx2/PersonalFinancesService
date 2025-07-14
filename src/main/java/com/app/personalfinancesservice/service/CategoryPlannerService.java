package com.app.personalfinancesservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryPlannerConverter;
import com.app.personalfinancesservice.converters.CategoryPlannerDTOConverter;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.CategoryPlannerFilter;
import com.app.personalfinancesservice.filter.CategoryPlannerSorter;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.GetListCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.personalfinance.api.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.personalfinance.api.domain.categoryplanner.output.DeleteCategoryPlannerResponse;
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

		CategoryPlanner newCategoryPlanner = CategoryPlannerConverter //
				.convert(request);

		CategoryPlannerDTO categoryPlannerDTO = categoryPlannerRepositoryFacade //
				.saveCategoryPlanner(newCategoryPlanner) //
				;

		return new CreateCategoryPlannerResponse() //
				.withCategoryPlanner(categoryPlannerDTO);
	}

	@Override
	public DeleteCategoryPlannerResponse deleteCategoryPlanner(DeleteCategoryPlannerRequest request) {

		// Get Category Planner
		CategoryPlannerDTO categoryPlanner = categoryPlannerRepositoryFacade //
				.getCategoryPlanner(request.getId(), request.getUserId());
		if (categoryPlanner == null) {
			// String message = String.format("CategoryPlanner of id %s could not be found", request.getId());
			// Add logs
			return new DeleteCategoryPlannerResponse().withSuccess(false);
		}

		categoryPlannerRepositoryFacade.deleteCategoryPlanner(categoryPlanner);

		return new DeleteCategoryPlannerResponse().withSuccess(true);
	}

	@Override
	public GetCategoryPlannerResponse getCategoryPlanner(GetCategoryPlannerRequest request) {

		CategoryPlannerDTO categoryPlanner = categoryPlannerRepositoryFacade //
				.getCategoryPlanner(request.getId(), request.getUserId());

		return new GetCategoryPlannerResponse() //
				.withCategoryPlanner(categoryPlanner);
	}

	@Override
	public GetListCategoryPlannerResponse getListCategoryPlanner(GetListCategoryPlannerRequest request) {

		// Check budget
		if (!budgetRepositoryFacade.budgetExists(request.getBudgetId(), request.getUserId())) {
			throw new NotFoundException(CATEGORY_PLANNER, BUDGET_ID_LABEL, request.getBudgetId());
		}

		List<CategoryPlannerDTO> categoryPlanners = categoryPlannerRepositoryFacade //
				.getCategoriesPlanner(request.getUserId(), request.getBudgetId());

		if (categoryPlanners == null || categoryPlanners.isEmpty()) {
			return new GetListCategoryPlannerResponse() //
					.withCategoryPlanners(new ArrayList<>());
		}
		// Apply filter
		List<CategoryPlannerDTO> filteredCategoryPlanners = CategoryPlannerFilter //
				.filterByTransactionType(categoryPlanners, request.getTransactionType());

		filteredCategoryPlanners = CategoryPlannerFilter //
				.filterByName(filteredCategoryPlanners, request.getCategoryName());

		// Apply sort
		List<CategoryPlannerDTO> sortedCategoryPlanner = CategoryPlannerSorter //
				.sort(filteredCategoryPlanners, request.getSortBy(), request.getSortDirection());

		return new GetListCategoryPlannerResponse() //
				.withCategoryPlanners(sortedCategoryPlanner);
	}

	@Override
	public UpdateCategoryPlannerResponse updateCategoryPlanner(UpdateCategoryPlannerRequest request) {

		CategoryPlannerDTO categoryPlannerRequest = CategoryPlannerDTOConverter.convert(request);

		// Update category planner
		CategoryPlannerDTO updatedCategoryPlanner = categoryPlannerRepositoryFacade //
				.updateCategoryPlanner(categoryPlannerRequest);

		return new UpdateCategoryPlannerResponse() //
				.withCategoryPlanner(updatedCategoryPlanner);
	}
}
