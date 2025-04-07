package com.app.personalfinancesservice.domain.http;

public class HttpRoutes {

	private static final String API_ROOT = "/api/personal-finance";
	public static final String PORTFOLIO = API_ROOT + "/portfolio";
	public static final String BUDGET = API_ROOT + "/budget";
	public static final String CURRENCY = API_ROOT + "/currency";


	private HttpRoutes() {
		// Do nothing on purpose
	}
}
