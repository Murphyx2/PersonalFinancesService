package com.app.personalfinancesservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.CategoryPlannerConverter;
import com.app.personalfinancesservice.converters.GetCategoryPlannerRequestConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.category.input.GetCategoryRequest;
import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.categoryplanner.input.CreateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetListCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.output.CreateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.GetListCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.categoryplanner.output.UpdateCategoryPlannerResponse;
import com.app.personalfinancesservice.domain.service.CategoryPlannerServiceBase;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.CategoryPlannerFilter;
import com.app.personalfinancesservice.filter.CategoryPlannerSorter;
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

		// Get Category Planner
		GetCategoryPlannerRequest categoryRequest = GetCategoryPlannerRequestConverter.convert(request);

		CategoryPlanner categoryPlanner = getCategoryPlanner(categoryRequest).getCategoryPlanner();
		if (categoryPlanner == null) {
			String message = String.format("CategoryPlanner of id %s could not be found", request.getId());
			throw new NotFoundException(CATEGORY_PLANNER, message);
		}

		categoryPlannerRepository.delete(categoryPlanner);
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
	public GetListCategoryPlannerResponse getListCategoryPlanner(GetListCategoryPlannerRequest request) {

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
		List<CategoryPlanner> filteredCategoryPlanners = CategoryPlannerFilter //
				.filterByTransactionType(categoryPlanners, request.getTransactionType());

		filteredCategoryPlanners = CategoryPlannerFilter //
				.filterByName(filteredCategoryPlanners, request.getCategoryName());

		// Apply sort
		List<CategoryPlanner> sortedCategoryPlanner = CategoryPlannerSorter //
				.sort(filteredCategoryPlanners, request.getSortBy(), request.getSortDirection());

		return new GetListCategoryPlannerResponse().withCategoryPlanners(sortedCategoryPlanner);
	}

	@Override
	public UpdateCategoryPlannerResponse updateCategoryPlanner(UpdateCategoryPlannerRequest request) {

		// Check if CategoryPlanner exists
		GetCategoryPlannerRequest categoryPlannerRequest = GetCategoryPlannerRequestConverter.convert(request);

		CategoryPlanner oldCategoryPlanner = getCategoryPlanner(categoryPlannerRequest).getCategoryPlanner();
		if (oldCategoryPlanner == null) {
			String message = String.format("CategoryPlanner of id %s could not be found", request.getId());
			throw new NotFoundException(CATEGORY_PLANNER, message);
		}

		// Check if new category exists
		GetCategoryRequest categoryRequest = new GetCategoryRequest() //
				.withId(request.getCategoryId()) //
				.withUserId(request.getUserId());
		List<Category> category = categoryService //
				.getCategory(categoryRequest).getCategory();
		if (category.isEmpty()) {
			String message = String.format("Category of id %s could not be found", request.getCategoryId());
			throw new NotFoundException(CATEGORY_PLANNER, message);
		}
		// Convert and save
		CategoryPlanner updatedCategoryPlanner = CategoryPlannerConverter //
				.convert(request, category.getFirst(), oldCategoryPlanner);

		return new UpdateCategoryPlannerResponse() //
				.withCategoryPlanner(categoryPlannerRepository.save(updatedCategoryPlanner));
	}
}
