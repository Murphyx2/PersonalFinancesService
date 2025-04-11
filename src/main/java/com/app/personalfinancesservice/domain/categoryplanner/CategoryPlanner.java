package com.app.personalfinancesservice.domain.categoryplanner;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.category.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPlanner {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "budget_id", insertable = false, updatable = false)
	@JsonBackReference
	private Budget budget;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	private Double plannedAmount;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public CategoryPlanner withId(UUID id) {
		this.setId(id);
		return this;
	}

	public CategoryPlanner withBudget(Budget budget) {
		this.setBudget(budget);
		return this;
	}

	public CategoryPlanner withCategory(Category category) {
		this.setCategory(category);
		return this;
	}

	public CategoryPlanner withPlannedAmount(Double plannedAmount) {
		this.setPlannedAmount(plannedAmount);
		return this;
	}

	public CategoryPlanner withCreatedAt(LocalDateTime createdAt) {
		this.setCreatedAt(createdAt);
		return this;
	}

	public CategoryPlanner withUpdatedAt(LocalDateTime updatedAt) {
		this.setUpdatedAt(updatedAt);
		return this;
	}
}
