package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class InvalidIdException extends RuntimeException {
	private final String location;
	private final String fieldName;
	private final String fieldValue;

	public InvalidIdException(String location, String fieldName, String fieldValue) {
		super(String.format("Invalid %s %s", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.location = location;
	}
}
