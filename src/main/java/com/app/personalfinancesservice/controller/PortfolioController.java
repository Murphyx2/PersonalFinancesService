package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetAllPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetAllPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.service.PortfolioService;

@RestController
@RequestMapping(HttpRoutes.PORTFOLIO)
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

	@GetMapping
	public ResponseEntity<GetAllPortfolioResponse> getAllPortfolio(@RequestHeader("X-User-id") String userId) {
		return ResponseEntity //
				.ok(portfolioService.getAllPortfolio(new GetAllPortfolioRequest().withUserId(userId)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetPortfolioResponse> getPortfolio(@RequestHeader("X-User-id") String userId, //
			@PathVariable String id) {
		return ResponseEntity //
				.ok(portfolioService.getPortfolio(new GetPortfolioRequest().withPortfolioId(id).withUserId(userId)));
	}
}
