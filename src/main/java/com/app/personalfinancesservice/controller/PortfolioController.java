package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.service.PortfolioService;

@RestController
@RequestMapping("/api/personal-finances")
public class PortfolioController {

	private final PortfolioService portfolioService;

	public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@PostMapping
	public ResponseEntity<CreatePortfolioResponse> createPortfolio(@RequestHeader("X-User-id") String id, //
			@RequestBody CreatePortfolioRequest request) {

		return ResponseEntity.ok(portfolioService.createPortfolio(id, request));
	}
}
