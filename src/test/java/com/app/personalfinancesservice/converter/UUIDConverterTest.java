package com.app.personalfinancesservice.converter;

import java.util.UUID;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.InvalidIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UUIDConverterTest {

	@Test
	void convertUUIDSuccess(){
		String testId = "22d26f83-d495-495a-8deb-b67406e42ad1";

		UUID testUUID = UUIDConverter.convert(testId, "testId", this.getClass().getName());

		assertEquals(testId, testUUID.toString());
	}

	@Test
	void convertUUIDFailure(){
		String invalidId = "no-uuid";
		String idName = "portfolioId";
		String className = this.getClass().getName();

		InvalidIdException invalidIdException = assertThrows(InvalidIdException.class, () -> {
			UUIDConverter.convert(invalidId, idName, className);
		});

		assertEquals(this.getClass().getName(), invalidIdException.getLocation());
		assertEquals(String.format("Invalid %s %s", idName, invalidId), invalidIdException.getMessage());

	}
}
