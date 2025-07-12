package com.app.personalfinancesservice.facade.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.CategoryConverter;
import com.app.personalfinancesservice.converters.CategoryDTOConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.repository.CategoryRepository;
import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.category.dto.CategoryDTO;
import com.personalfinance.api.domain.transaction.TransactionType;
import com.personalfinance.api.facade.CategoryRepositoryFacade;

@Component
public class CategoryRepositoryFacadeImpl implements CategoryRepositoryFacade {

	private static final String CATEGORY_LABEL = "CATEGORY";
	private static final String USER_ID_LABEL = "userId";
	private static final String CATEGORY_ID_LABEL = "categoryId";

	private final CategoryRepository categoryRepository;

	public CategoryRepositoryFacadeImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public boolean categoryExists(String userId, String name, TransactionType transactionType) {

		UUID userIdUUID = UUIDConverter.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		// Try to find Category by name
		return categoryRepository //
				.getCategoriesByUserId(userIdUUID) //
				.stream() //
				.anyMatch(category -> //
						category.getName().equals(name.toUpperCase()) //
								&& category.getTransactionType().equals(transactionType) //
				);

	}

	@Override
	public boolean categoryExists(String categoryId, String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		UUID categoryUUID = UUIDConverter //
				.convert(categoryId, CATEGORY_ID_LABEL, CATEGORY_LABEL);

		return categoryRepository //
				.getCategoryByIdAndUserId(categoryUUID, userIdUUID) //
				.isPresent();
	}

	@Override

	@Caching(evict = {
			@CacheEvict(value = "categoriesList", key = "#userId"),
			@CacheEvict(value = "categories", key = "#userId + '_' + #id")
	})
	public void deleteCategory(String id, String userId) {

		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		UUID categoryUUID = UUIDConverter //
				.convert(id, CATEGORY_ID_LABEL, CATEGORY_LABEL);

		//Get the category and remove it
		categoryRepository //
				.getCategoryByIdAndUserId(categoryUUID, userUUID) //
				.ifPresent(categoryRepository::delete);
	}

	@Override
	@Cacheable(value = "categoriesList", key = "#userId")
	public List<CategoryDTO> getCategories(String userId) {

		UUID userUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		return CategoryDTOConverter //
				.convertMany(categoryRepository.getCategoriesByUserId(userUUID));
	}

	@Override
	@Cacheable(value = "categories", key = "#userId + '_' + #id", unless = "#result == null")
	public CategoryDTO getCategory(String id, String userId) {
		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		UUID categoryId = UUIDConverter //
				.convert(id, CATEGORY_ID_LABEL, CATEGORY_LABEL);

		Optional<Category> category = categoryRepository //
				.getCategoryByIdAndUserId(categoryId, userIdUUID);

		return CategoryDTOConverter.convert(category.orElse(null));
	}

	@Override
	@CacheEvict(value = "categoriesList", key = "#category.userId")
	public CategoryDTO saveCategory(Category category) {

		if (category == null) {
			throw new MissingIdException(CATEGORY_LABEL, "category");
		}

		return CategoryDTOConverter //
				.convert(categoryRepository.save(category));
	}

	@Override
	@CacheEvict(value = "categoriesList", key = "#categoryDTO.userId")
	@CachePut(value = "categories", key = "#categoryDTO.userId + '_' + #categoryDTO.id")
	public CategoryDTO updateCategory(CategoryDTO categoryDTO) {

		if (categoryDTO == null) {
			throw new MissingIdException(CATEGORY_LABEL, "categoryDTO");
		}

		UUID userUUID = UUIDConverter //
				.convert(categoryDTO.getUserId(), USER_ID_LABEL, CATEGORY_LABEL);

		UUID categoryId = UUIDConverter //
				.convert(categoryDTO.getId(), CATEGORY_ID_LABEL, CATEGORY_LABEL);

		Category oldCategory = categoryRepository //
				.getCategoryByIdAndUserId(categoryId, userUUID) //
				.orElseThrow(() -> new MissingIdException(CATEGORY_LABEL, "category"));

		Category updatedCategory = categoryRepository //
				.save(CategoryConverter.convert(oldCategory, categoryDTO));

		return CategoryDTOConverter.convert(updatedCategory);
	}
}

