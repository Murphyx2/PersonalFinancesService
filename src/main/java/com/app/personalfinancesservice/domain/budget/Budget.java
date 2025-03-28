package com.app.personalfinancesservice.domain.budget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.transaction.Transaction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID userId;

	@Column(name = "portfolio_id")
	private UUID portfolioId;

	@ManyToOne
	@JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
	private Portfolio portfolio;

	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
	private List<Transaction> transactions;

	private String name;

	private String description;

	private LocalDateTime startAt;

	private LocalDateTime endAt;

	private LocalDateTime createdAt;

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
