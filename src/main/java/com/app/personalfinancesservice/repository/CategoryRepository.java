package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.personalfinancesservice.domain.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
	List<Category> getCategoriesByIdAndUserId(UUID id, UUID userId);

	List<Category> getCategoriesByUserId(UUID userId);
}
