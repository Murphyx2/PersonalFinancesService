package com.app.personalfinancesservice.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final String ERROR_KEY_NAME = "error";
	private static final String MESSAGE_LABEL = "message";

	@ExceptionHandler(BudgetNotFoundException.class)
	public ResponseEntity<Map<String, String>> handledBudgetNotFoundException(BudgetNotFoundException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getLocation());
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CreateNewItemException.class)
	public ResponseEntity<Map<String, String>> handledCreateNewCategoryException(CreateNewItemException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getLocation());
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CreateNewPortfolioException.class)
	public ResponseEntity<Map<String, String>> handledCreateNewPortfolioException(CreateNewPortfolioException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getFieldName());
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// At the moment, Only log the general exceptions.
	// As a rule, The other exception will log before being called.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handledGeneralException(Exception ex) {
		LOGGER.error(ex.getMessage(), ex);
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, "GENERAL_ERROR");
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidIdException.class)
	public ResponseEntity<Map<String, String>> handledInvalidIdException(InvalidIdException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getLocation());
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// There is a better way to deal with this
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handledMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getObjectName());
		error.put(MESSAGE_LABEL, String.format("%s %s", ex.getFieldError() != null ? ex.getFieldError().getField() : "", ex.getAllErrors().getFirst().getDefaultMessage()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingIdException.class)
	public ResponseEntity<Map<String, String>> handledMissingIdException(MissingIdException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getLocation());
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PortfolioNotFoundException.class)
	public ResponseEntity<Map<String, String>> handledPortfolioNotFoundException(PortfolioNotFoundException ex) {
		Map<String, String> error = new HashMap<>();
		error.put(ERROR_KEY_NAME, ex.getLocation());
		error.put(MESSAGE_LABEL, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
