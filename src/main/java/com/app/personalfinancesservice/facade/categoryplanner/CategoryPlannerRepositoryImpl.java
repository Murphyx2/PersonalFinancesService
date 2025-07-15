package com.app.personalfinancesservice.facade.categoryplanner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.CategoryPlannerConverter;
import com.app.personalfinancesservice.converters.CategoryPlannerDTOConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.CreateNewItemException;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.repository.CategoryPlannerJPARepository;
import com.app.personalfinancesservice.repository.CategoryRepository;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.categoryplanner.CategoryPlanner;
import com.personalfinance.api.domain.categoryplanner.dto.CategoryPlannerDTO;
import com.personalfinance.api.facade.CategoryPlannerRepositoryFacade;

import static com.app.personalfinancesservice.domain.http.HttpRoutes.CATEGORY_PLANNER;

@Component
public class CategoryPlannerRepositoryImpl implements CategoryPlannerRepositoryFacade {

	private static final String CATEGORY_PLANNER_LABEL = "CATEGORY_PLANNER";
	private static final String USER_ID_LABEL = "userId";
	private static final String BUDGET_ID_LABEL = "budgetId";
	private static final String CATEGORY_ID_LABEL = "categoryId";
	private static final String CATEGORY_PLANNER_ID_LABEL = "categoryPlannerId";

	private final CategoryPlannerJPARepository categoryPlannerJPARepository;
	private final CategoryRepository categoryRepository;

	public CategoryPlannerRepositoryImpl( //
			CategoryPlannerJPARepository categoryPlannerJPARepository //
			, CategoryRepository categoryRepository) {
		this.categoryPlannerJPARepository = categoryPlannerJPARepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public boolean categoryPlannerExists(String categoryId, String budgetId, String userId) {

		UUID categoryUUID = UUIDConverter //
				.convert(categoryId, CATEGORY_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID budgetUUID = UUIDConverter //
				.convert(budgetId, BUDGET_ID_LABEL, CATEGORY_PLANNER_LABEL);

		List<CategoryPlanner> categoryPlanners = categoryPlannerJPARepository //
				.getCategoryPlannersByUserIdAndBudgetId(userUUID, budgetUUID);

		return categoryPlanners //
				.stream() //
				.anyMatch(categoryPlanner -> //
						categoryPlanner.getCategory().getId().equals(categoryUUID) //
				);
	}

	@Override
	public boolean categoryPlannerExists(String id, String userId) {

		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID categoryPlannerId = UUIDConverter //
				.convert(id, CATEGORY_PLANNER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		Optional<CategoryPlanner> categoryPlanner = categoryPlannerJPARepository //
				.getCategoryPlannerByIdAndUserId(categoryPlannerId, userUUID);

		return categoryPlanner.isPresent();
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "categoryPlannerList", key = "#categoryPlannerDTO.budgetId"),
			@CacheEvict(value = "categoryPlanners", key = "#categoryPlannerDTO.userId + '_' + #categoryPlannerDTO.id")
	})
	public void deleteCategoryPlanner(CategoryPlannerDTO categoryPlannerDTO) {

		UUID userUUID = UUIDConverter //
				.convert(categoryPlannerDTO.getUserId(), USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID categoryPlannerUUID = UUIDConverter //
				.convert(categoryPlannerDTO.getId(), CATEGORY_PLANNER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		Optional<CategoryPlanner> categoryPlanner = categoryPlannerJPARepository //
				.getCategoryPlannerByIdAndUserId(categoryPlannerUUID, userUUID);

		categoryPlanner.ifPresent(categoryPlannerJPARepository::delete);

	}

	@Override
	@Cacheable(value = "categoryPlannerList", key = "#budgetId", unless = "#result.empty")
	public List<CategoryPlannerDTO> getCategoriesPlanner(String userId, String budgetId) {
		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID budgetUUID = UUIDConverter //
				.convert(budgetId, BUDGET_ID_LABEL, CATEGORY_PLANNER_LABEL);

		List<CategoryPlanner> categories = categoryPlannerJPARepository //
				.getCategoryPlannersByUserIdAndBudgetId(userUUID, budgetUUID);

		return CategoryPlannerDTOConverter.convertMany(categories);
	}

	@Override
	@Cacheable(value = "categoryPlanners", key = "#userId + '_' + #id", unless = "#result == null")
	public CategoryPlannerDTO getCategoryPlanner(String id, String userId) {

		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID categoryPlannerId = UUIDConverter //
				.convert(id, CATEGORY_PLANNER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		Optional<CategoryPlanner> categoryPlanner = categoryPlannerJPARepository //
				.getCategoryPlannerByIdAndUserId(categoryPlannerId, userUUID);

		return CategoryPlannerDTOConverter //
				.convert(categoryPlanner.orElse(null));
	}

	@Override
	@CacheEvict(value = "categoryPlannerList", key = "#categoryPlanner.budgetId")
	public CategoryPlannerDTO saveCategoryPlanner(CategoryPlanner categoryPlanner) {

		if (categoryPlanner == null) {
			throw new MissingIdException(CATEGORY_PLANNER_LABEL, "categoryPlanner");
		}

		Optional<Category> category = categoryRepository //
				.getCategoryByIdAndUserId(categoryPlanner.getCategory().getId(), //
						categoryPlanner.getUserId() //
				);

		// Check if Category exists
		if (category.isEmpty()) {
			throw new NotFoundException(CATEGORY_PLANNER, CATEGORY_ID_LABEL //
					, categoryPlanner.getUserId().toString());
		}

		// Check if CategoryPlanner already exists
		if (this.categoryPlannerExists( //
				categoryPlanner.getCategory().getId().toString(), //
				categoryPlanner.getBudgetId().toString(),  //
				categoryPlanner.getUserId().toString()) //
		) {
			String message = String.format("CategoryPlanner for category of name %s already exists", //
					category.get().getName());
			throw new CreateNewItemException(CATEGORY_PLANNER, message);
		}

		categoryPlanner.withCategory(category.get());

		return CategoryPlannerDTOConverter //
				.convert(categoryPlannerJPARepository.save(categoryPlanner));
	}

	@Override
	@CacheEvict(value = "categoryPlannerList", key = "#result.budgetId")
	@CachePut(value = "categoryPlanners", key = "#categoryPlannerDTO.userId + '_' + #categoryPlannerDTO.id", unless = "#result == null")
	public CategoryPlannerDTO updateCategoryPlanner(CategoryPlannerDTO categoryPlannerDTO) {

		UUID userUUID = UUIDConverter //
				.convert(categoryPlannerDTO.getUserId() //
						, USER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		UUID categoryPlannerUUID = UUIDConverter //
				.convert(categoryPlannerDTO.getId() //
						, CATEGORY_PLANNER_ID_LABEL, CATEGORY_PLANNER_LABEL);

		CategoryPlanner oldCategoryPlanner = categoryPlannerJPARepository //
				.getCategoryPlannerByIdAndUserId(categoryPlannerUUID, userUUID) //
				.orElseThrow( //
						() -> new NotFoundException(CATEGORY_PLANNER, CATEGORY_PLANNER_ID_LABEL, categoryPlannerUUID.toString()) //
				);

		UUID categoryUUID = UUIDConverter //
				.convert(categoryPlannerDTO.getCategory().getId() //
						, CATEGORY_ID_LABEL, CATEGORY_PLANNER_LABEL);

		Category category = categoryRepository //
				.getCategoryByIdAndUserId(categoryUUID, userUUID) //
				.orElseThrow( //
						() -> new NotFoundException(CATEGORY_PLANNER, CATEGORY_ID_LABEL, categoryUUID.toString()) //
				);

		CategoryPlanner categoryPlannerToUpdate = CategoryPlannerConverter //
				.convert(categoryPlannerDTO, category, oldCategoryPlanner);

		CategoryPlanner updatedCategoryPlanner = categoryPlannerJPARepository //
				.save(categoryPlannerToUpdate);

		return CategoryPlannerDTOConverter.convert(updatedCategoryPlanner);
	}
}
