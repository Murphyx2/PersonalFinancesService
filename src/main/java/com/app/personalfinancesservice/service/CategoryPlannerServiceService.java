package com.app.personalfinancesservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryPlannerConverter;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.UpdateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.service.CategoryPlannerServiceBase;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.repository.CategoryPlannerRepository;

@Service
public class CategoryPlannerServiceService implements CategoryPlannerServiceBase {

	private static final String CATEGORY_PLANNER = "CATEGORY_PLANNER";
	private static final String BUDGET_ID_LABEL = "budgetId";
	private static final String CATEGORY_ID_LABEL = "categoryId";

	CategoryPlannerRepository categoryPlannerRepository;
	BudgetService budgetService;
	CategoryService categoryService;

	public CategoryPlannerServiceService(CategoryPlannerRepository categoryPlannerRepository, //
			BudgetService budgetService, CategoryService categoryService) {
		this.categoryPlannerRepository = categoryPlannerRepository;
		this.budgetService = budgetService;
		this.categoryService = categoryService;
	}

	@Override
	public CreateCategoryPlannerResponse createCategoryPlanner(CreateCategoryPlannerRequest request) {

		// Check if budget exists
		List<Budget> budget = budgetService.getBudgets(new GetBudgetsRequest() //
				.withId(request.getBudgetId()) //
				.withUserId(request.getUserId()) //
		).getBudgets();
		if (budget.isEmpty()) {
			throw new NotFoundException(CATEGORY_PLANNER, BUDGET_ID_LABEL, request.getBudgetId());
		}

		// Check if Category exists
		List<Category> category = categoryService.getCategory(new GetCategoryRequest() //
				.withId(request.getCategoryId()) //
				.withUserId(request.getUserId()) //
		).getCategory();
		if (category.isEmpty()) {
			throw new NotFoundException(CATEGORY_PLANNER, CATEGORY_ID_LABEL, request.getBudgetId());
		}

		// TODO: Check if there is another CategoryPlanner within the same budget with the same CategoryId

		CategoryPlanner newCategoryPlanner = CategoryPlannerConverter //
				.convert(request) //
				.withBudget(budget.getFirst()) //
				.withCategory(category.getFirst());

		return new CreateCategoryPlannerResponse() //
				.withCategoryPlanner(categoryPlannerRepository.save(newCategoryPlanner));
	}

	@Override
	public void deleteCategory(DeleteCategoryPlannerRequest request) {

	}

	@Override
	public GetCategoryPlannerResponse getCategory(GetCategoryPlannerRequest request) {
		return null;
	}

	@Override
	public UpdateCategoryPlannerResponse updateCategory(UpdateCategoryPlannerRequest request) {
		return null;
	}
}
