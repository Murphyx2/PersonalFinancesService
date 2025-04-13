package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
	private final String location;
	private final String fieldName;
	private final String fieldValue;

	public NotFoundException(String location, String fieldName, String fieldValue) {
		super(String.format("Error %s of value %s could not be found", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.location = location;
	}

	public NotFoundException(String location, String fieldValue) {
		super(fieldValue);
		this.fieldName = null;
		this.fieldValue = fieldValue;
		this.location = location;
	}


}
