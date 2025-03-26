package com.app.personalfinancesservice.domain.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.Currency.CurrencyType;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.transaction.transactiontype.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID userId;

	@Column(name = "budget_id")
	private UUID budgetId;

	@ManyToOne
	@JoinColumn(name = "budget_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Budget budget;

	@OneToOne
	@JoinColumn(name = "currency_type_id", referencedColumnName = "id", nullable = false, updatable = true)
	private CurrencyType currency;

	@OneToOne
	@JoinColumn(name = "transaction_type_id", referencedColumnName = "id", nullable = false, updatable = true)
	private TransactionType transactionType;

	private String name;

	private String description;

	private Double amount;

	private LocalDateTime date;

	public Transaction withAmount(Double amount) {
		this.setAmount(amount);
		return this;
	}

	public Transaction withBudget(Budget budget) {
		this.setBudget(budget);
		return this;
	}

	public Transaction withBudgetId(UUID budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public Transaction withCurrency(CurrencyType currency) {
		this.setCurrency(currency);
		return this;
	}

	public Transaction withDate(LocalDateTime date) {
		this.setDate(date);
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

	public Transaction withName(String name) {
		this.setName(name);
		return this;
	}

	public Transaction withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public Transaction withUserId(UUID userId) {
		this.setUserId(userId);
		return this;
	}
}
