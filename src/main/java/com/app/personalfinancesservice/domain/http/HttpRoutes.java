package com.app.personalfinancesservice.domain.http;

public class HttpRoutes {

	private static final String API_ROOT = "/api/personal-finance";
	public static final String PORTFOLIO = API_ROOT + "/portfolio";
	public static final String BUDGET = API_ROOT + "/budget";
	public static final String CURRENCY = API_ROOT + "/currency";
	public static final String TRANSACTIONS = API_ROOT + "/transactions";
	public static final String CATEGORY = API_ROOT + "/category";
	public static final String CATEGORY_PLANNER = API_ROOT + "/category_planner";


	private HttpRoutes() {
		// Do nothing on purpose
	}
}
