package com.app.personalfinancesservice.domain.transaction.input;

import java.time.LocalDateTime;

import com.app.personalfinancesservice.domain.currency.DefaultCurrencies;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionRequest {

	private String userId;
	@NotBlank(message = "budgetId is required")
	private String budgetId;
	@NotBlank(message = "categoryId is required")
	private String categoryId;
	private String description;
	private Double amount;
	@NotNull(message = "currencyCode is required")
	private DefaultCurrencies currencyCode;
	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime transactionDate;

	public CreateTransactionRequest withAmount(Double amount) {
		this.setAmount(amount);
		return this;
	}

	public CreateTransactionRequest withBudgetId(String budgetId) {
		this.setBudgetId(budgetId);
		return this;
	}

	public CreateTransactionRequest withCategoryId(String categoryId) {
		this.setCategoryId(categoryId);
		return this;
	}

	public CreateTransactionRequest withCurrencyCode(DefaultCurrencies currencyCode) {
		this.setCurrencyCode(currencyCode);
		return this;
	}

	public CreateTransactionRequest withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public CreateTransactionRequest withTransactionDate(LocalDateTime transactionDate) {
		this.setTransactionDate(transactionDate);
		return this;
	}

	public CreateTransactionRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
