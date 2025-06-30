package com.app.personalfinancesservice.facade.category;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.category.Category;
import com.personalfinance.api.domain.transaction.TransactionType;

@Repository
public interface CategoryRepositoryFacade {

	boolean categoryExists(String userId, String name, TransactionType transactionType);

	boolean categoryExists(String categoryId, String userId);

	Category getCategory(String id,  String userId);

	List<Category> getCategories(String userId);

	boolean deleteCategory(Category category);

	Category saveCategory(Category category);

}
