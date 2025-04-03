package com.app.personalfinancesservice.converters;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.personalfinancesservice.exceptions.InvalidIdException;

public class UUIDConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(UUIDConverter.class);

	public static UUID convert(String id, String idName, String location) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			LOGGER.error(location, e);
			throw new InvalidIdException(location, idName, id);
		}
		return uuid;
	}

	private UUIDConverter() {
		// Empty on purpose
	}
}
