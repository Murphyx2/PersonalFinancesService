package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class CreateNewPortfolioException extends RuntimeException {
	private final String fieldName;
	private final String fieldValue;

	//TODO: Consider the exceptions messages
	public CreateNewPortfolioException(String fieldName, String fieldValue) {
		super("Error creating portfolio");
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
