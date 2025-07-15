package com.app.personalfinancesservice.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
	private static final String REQUEST_ID_HEADER = "X-Request-ID";

	private static final String REQUEST_HEADER_TEMPLATE = "Request | ID: %s | Method: %s | Path: %s | Headers: %s";
	private static final String RESPONSE_HEADER_TEMPLATE = "Response | ID: %s | Status: %s | Headers: %s";

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	@SuppressWarnings("squid:S2629")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) //
			throws ServletException, IOException { //

		String requestId = request.getHeader(REQUEST_ID_HEADER);
		if (requestId == null) {
			requestId = "N/A";
		}

		// Get the headers
		Map<String, String> requestHeaders = new HashMap<>();
		Collections.list(request.getHeaderNames()).forEach(name -> { //
			if (!AUTHORIZATION_HEADER.equalsIgnoreCase(name)) { //
				requestHeaders.put(name, request.getHeader(name)); //
			} //
		});

		LOGGER.info(String.format(REQUEST_HEADER_TEMPLATE, //
				requestId, //
				request.getMethod(), //
				request.getRequestURI(), //
				requestHeaders //
		));

		if (request.getRequestURI().startsWith("/swagger-ui") || request.getRequestURI().startsWith("/v3/api-docs")) {
			filterChain.doFilter(request, response);
			return;
		}

		filterChain.doFilter(request, response);

		LOGGER.info(String.format(RESPONSE_HEADER_TEMPLATE, //
				requestId, //
				response.getStatus(), //
				response.getHeaderNames() //
		));
	}
}
