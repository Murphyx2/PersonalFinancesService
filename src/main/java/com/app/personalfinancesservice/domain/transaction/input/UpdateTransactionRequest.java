package com.app.personalfinancesservice.domain.transaction.input;

import java.time.LocalDateTime;

import com.app.personalfinancesservice.domain.currency.DefaultCurrencies;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTransactionRequest {

	private String id;
	private String userId;
	private String categoryId;
	private Double amount;
	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime transactionDate;
	private DefaultCurrencies currencyCode;

	public UpdateTransactionRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public UpdateTransactionRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}

	public UpdateTransactionRequest withCategoryId(String categoryId) {
		this.setCategoryId(categoryId);
		return this;
	}

	public UpdateTransactionRequest withAmount(Double amount) {
		this.setAmount(amount);
		return this;
	}

	public UpdateTransactionRequest withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public UpdateTransactionRequest withTransactionDate(LocalDateTime transactionDate) {
		this.setTransactionDate(transactionDate);
		return this;
	}

	public UpdateTransactionRequest withCurrencyCode(DefaultCurrencies currencyCode) {
		this.setCurrencyCode(currencyCode);
		return this;
	}
}
