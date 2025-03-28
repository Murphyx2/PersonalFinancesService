package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class InvalidUserIdException extends RuntimeException {
	private final String fieldName;
	private final String fieldValue;

	public InvalidUserIdException(String fieldName, String fieldValue) {
		super("Invalid UserId provided");
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
