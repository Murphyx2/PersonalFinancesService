package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	boolean existsByIdAndUserId(UUID id, UUID userId);

	List<Category> getCategoriesByUserId(UUID userId);

	Optional<Category> getCategoryByIdAndUserId(UUID id, UUID userId);
}
