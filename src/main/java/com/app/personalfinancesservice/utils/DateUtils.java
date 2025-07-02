package com.app.personalfinancesservice.utils;

import java.time.LocalDateTime;

public class DateUtils {

	public static boolean isStartDateGreaterThanStartDate(LocalDateTime endDate, LocalDateTime startDate) {
		return startDate.isAfter(endDate);
	}

	private DateUtils() {
		// Empty on purpose
	}
}
