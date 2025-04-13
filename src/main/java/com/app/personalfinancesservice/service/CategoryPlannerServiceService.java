package com.app.personalfinancesservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryPlannerConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannersRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannersResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.UpdateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.service.CategoryPlannerServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
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

	public boolean categoryPlannerAlreadyExists(UUID categoryId, UUID budgetId) {
		return categoryPlannerRepository.existsByCategory_IdAndBudgetId(categoryId, budgetId);
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

		// Check if CategoryPlanner already exists
		if (categoryPlannerAlreadyExists(category.getFirst().getId(), budget.getFirst().getId())) {
			String message = String.format("CategoryPlanner of name %s and type %s already exists", //
					category.getFirst().getName(), //
					category.getFirst().getTransactionType());
			throw new CreateNewItemException(CATEGORY_PLANNER, message);
		}

		CategoryPlanner newCategoryPlanner = CategoryPlannerConverter //
				.convert(request) //
				.withBudgetId(budget.getFirst().getId()) //
				.withCategory(category.getFirst());

		return new CreateCategoryPlannerResponse() //
				.withCategoryPlanner(categoryPlannerRepository.save(newCategoryPlanner));
	}

	@Override
	public void deleteCategoryPlanner(DeleteCategoryPlannerRequest request) {
		// Empty waiting for implementation
	}

	@Override
	public GetCategoryPlannerResponse getCategoryPlanner(GetCategoryPlannerRequest request) {

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", CATEGORY_PLANNER);

		UUID id = UUIDConverter //
				.convert(request.getId(), "categoryPlannerId", CATEGORY_PLANNER);

		CategoryPlanner categoryPlanner = categoryPlannerRepository //
				.getCategoryPlannerByIdAndUserId(id, userId);

		if (categoryPlanner == null) {
			throw new NotFoundException(CATEGORY_PLANNER, "categoryPlanner", request.getId());
		}

		return new GetCategoryPlannerResponse().withCategoryPlanner(categoryPlanner);
	}

	@Override
	public GetCategoryPlannersResponse getCategoryPlanners(GetCategoryPlannersRequest request) {

		UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", CATEGORY_PLANNER);

		// Get budget
		List<Budget> budget = budgetService.getBudgets(new GetBudgetsRequest() //
				.withUserId(request.getUserId()) //
				.withId(request.getBudgetId()) //
		).getBudgets();

		if (budget.isEmpty()) {
			throw new NotFoundException(CATEGORY_PLANNER, BUDGET_ID_LABEL, request.getBudgetId());
		}

		List<CategoryPlanner> categoryPlanners = categoryPlannerRepository //
				.getCategoryPlannerByUserIdAndBudget(userId, budget.getFirst());

		if (categoryPlanners == null || categoryPlanners.isEmpty()) {
			throw new NotFoundException(CATEGORY_PLANNER, "categoryPlanner on budgetId", request.getBudgetId());
		}
		// Apply filter

		// Apply sort

		return new GetCategoryPlannersResponse().withCategoryPlanners(categoryPlanners);
	}

	@Override
	public UpdateCategoryPlannerResponse updateCategory(UpdateCategoryPlannerRequest request) {
		return null;
	}
}
