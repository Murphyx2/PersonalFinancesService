package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class BudgetNotFoundException extends RuntimeException {
	private final String location;
	private final String fieldName;
	private final String fieldValue;

	public BudgetNotFoundException(String location, String fieldName, String fieldValue) {
		super(String.format("Budget from %s %s not found", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.location = location;
	}

}
