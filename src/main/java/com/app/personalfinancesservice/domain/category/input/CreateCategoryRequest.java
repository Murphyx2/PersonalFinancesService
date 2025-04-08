package com.app.personalfinancesservice.domain.category.input;

import com.app.personalfinancesservice.domain.transaction.TransactionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryRequest {

	@NotEmpty
	private String name;

	private String userId;
	@NotNull
	private TransactionType transactionType;

	public CreateCategoryRequest withName(String name) {
		this.setName(name);
		return this;
	}

	public CreateCategoryRequest withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public CreateCategoryRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
