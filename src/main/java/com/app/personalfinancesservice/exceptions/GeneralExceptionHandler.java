package com.app.personalfinancesservice.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

	private static final String ERROR_KEY_NAME = "error";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handledGeneralException(Exception ex){
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, "General Exception");
		error.put("message", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
