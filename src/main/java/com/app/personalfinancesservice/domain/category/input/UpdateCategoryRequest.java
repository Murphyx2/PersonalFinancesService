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
public class UpdateCategoryRequest {

	private String id;
	private String userId;

	@NotEmpty
	private String name;

	@NotNull
	private TransactionType transactionType;

	public UpdateCategoryRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public UpdateCategoryRequest withName(String name) {
		this.setName(name);
		return this;
	}

	public UpdateCategoryRequest withTransactionType(TransactionType transactionType) {
		this.setTransactionType(transactionType);
		return this;
	}

	public UpdateCategoryRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}
}
