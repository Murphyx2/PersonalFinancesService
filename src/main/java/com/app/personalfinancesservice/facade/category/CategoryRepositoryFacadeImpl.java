package com.app.personalfinancesservice.facade.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.repository.CategoryRepository;
import com.personalfinance.api.domain.category.Category;
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
						category.getName().equals(name) //
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
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}

	@Override
	public List<Category> getCategories(String userId) {

		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		return categoryRepository.getCategoriesByUserId(userIdUUID);
	}

	@Override
	public Category getCategory(String id, String userId) {
		UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, CATEGORY_LABEL);

		UUID categoryId = UUIDConverter //
				.convert(id, CATEGORY_ID_LABEL, CATEGORY_LABEL);

		Optional<Category> category = categoryRepository //
				.getCategoryByIdAndUserId(categoryId, userIdUUID);

		return category.orElse(null);
	}

	@Override
	public Category saveCategory(Category category) {

		if (category == null) {
			throw new MissingIdException(CATEGORY_LABEL, "category");
		}

		return categoryRepository.save(category);
	}
}

