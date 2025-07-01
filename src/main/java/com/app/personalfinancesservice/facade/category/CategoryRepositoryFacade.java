package com.app.personalfinancesservice.facade.category;

import java.util.List;

import org.springframework.stereotype.Component;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.transaction.TransactionType;

@Component
public interface CategoryRepositoryFacade {

	boolean categoryExists(String userId, String name, TransactionType transactionType);

	boolean categoryExists(String categoryId, String userId);

	boolean deleteCategory(Category category);

	List<Category> getCategories(String userId);

	Category getCategory(String id, String userId);

	Category saveCategory(Category category);

}
