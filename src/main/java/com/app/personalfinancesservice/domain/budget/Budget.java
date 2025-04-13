package com.app.personalfinancesservice.domain.budget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.personalfinancesservice.domain.categoryplanner.CategoryPlanner;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID userId;

	@Column(name = "portfolio_id")
	private UUID portfolioId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
	@JsonBackReference
	private Portfolio portfolio;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "budget", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Transaction> transactions;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "budget", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CategoryPlanner> categoryPlanners;

	private String name;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime startAt;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime endAt;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime updatedAt;

	public Budget withCreatedAt(LocalDateTime createdAt) {
		this.setCreatedAt(createdAt);
		return this;
	}

	public Budget withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public Budget withEndAt(LocalDateTime endAt) {
		this.setEndAt(endAt);
		return this;
	}

	public Budget withId(UUID id) {
		this.setId(id);
		return this;
	}

	public Budget withName(String name) {
		this.setName(name);
		return this;
	}

	public Budget withPortfolio(Portfolio portfolio) {
		this.setPortfolio(portfolio);
		return this;
	}

	public Budget withPortfolioId(UUID portfolioId) {
		this.setPortfolioId(portfolioId);
		return this;
	}

	public Budget withStartAt(LocalDateTime startAt) {
		this.setStartAt(startAt);
		return this;
	}

	public Budget withTransactions(List<Transaction> transactions) {
		this.setTransactions(transactions);
		return this;
	}

	public Budget withUpdatedAt(LocalDateTime updatedAt) {
		this.setUpdatedAt(updatedAt);
		return this;
	}

	public Budget withUserId(UUID userId) {
		this.setUserId(userId);
		return this;
	}
}
