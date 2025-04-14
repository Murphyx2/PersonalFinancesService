package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class CreateNewItemException extends RuntimeException {
	private final String fieldName;
	private final String fieldValue;
	private final String location;

	public CreateNewItemException(String fieldName, String fieldValue, String location) {
		super(String.format("Error creating %s, %s", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.location = location;
	}

	public CreateNewItemException(String location, String fieldValue) {
		super(fieldValue);
		this.fieldValue = fieldValue;
		this.fieldName = null;
		this.location = location;
	}
}
