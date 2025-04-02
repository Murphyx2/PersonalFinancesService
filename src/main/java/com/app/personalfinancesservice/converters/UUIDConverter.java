package com.app.personalfinancesservice.converters;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.personalfinancesservice.exceptions.InvalidIdException;

public class UUIDConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(UUIDConverter.class);
	private static final String EXCEPTION_LABEL = "PORTFOLIO";

	public static UUID convert(String id, String idName) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			LOGGER.error(EXCEPTION_LABEL, e);
			throw new InvalidIdException(EXCEPTION_LABEL, idName, id);
		}
		return uuid;
	}

	private UUIDConverter() {
		// Empty on purpose
	}
}
