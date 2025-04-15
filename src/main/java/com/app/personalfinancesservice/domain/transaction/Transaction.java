package com.app.personalfinancesservice.domain.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.category.Category;
import com.app.personalfinancesservice.domain.currency.DefaultCurrencies;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID userId;

	@Column(name = "budget_id", nullable = false)
	private UUID budgetId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "budget_id", insertable = false, updatable = false)
	@JsonBackReference
	private Budget budget;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonManagedReference
	private Category category;

	private DefaultCurrencies currencyCode;

	private String description;

	private Double amount;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime transactionDate;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime updatedAt;

	public Transaction withBudgetId(UUID budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public Transaction withAmount(Double amount) {
		this.setAmount(amount);
		return this;
	}

	public Transaction withBudget(Budget budget) {
		this.setBudget(budget);
		return this;
	}

	public Transaction withCategory(Category category) {
		this.setCategory(category);
		return this;
	}

	public Transaction withCreatedAt(LocalDateTime createdAt) {
		this.setCreatedAt(createdAt);
		return this;
	}

	public Transaction withCurrencyCode(DefaultCurrencies currencyCode) {
		this.setCurrencyCode(currencyCode);
		return this;
	}

	public Transaction withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public Transaction withId(UUID id) {
		this.setId(id);
		return this;
	}

	public Transaction withTransactionDate(LocalDateTime transactionDate) {
		this.setTransactionDate(transactionDate);
		return this;
	}

	public Transaction withUpdatedAt(LocalDateTime updatedAt) {
		this.setUpdatedAt(updatedAt);
		return this;
	}

	public Transaction withUserId(UUID userId) {
		this.setUserId(userId);
		return this;
	}
}
