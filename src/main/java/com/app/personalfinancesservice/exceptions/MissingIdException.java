package com.app.personalfinancesservice.exceptions;

import lombok.Getter;

@Getter
public class MissingIdException extends RuntimeException {
	private final String location;
	private final String fieldName;

	public MissingIdException(String location, String fieldName) {
		super(String.format("Missing %s", fieldName));
		this.fieldName = fieldName;
		this.location = location;
	}
}
