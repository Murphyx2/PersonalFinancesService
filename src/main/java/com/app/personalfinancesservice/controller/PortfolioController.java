package com.app.personalfinancesservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.personalfinancesservice.domain.filter.SortBy;
import com.app.personalfinancesservice.domain.filter.SortDirection;
import com.app.personalfinancesservice.domain.http.HttpRoutes;
import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.DeletePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfoliosRequest;
import com.app.personalfinancesservice.domain.portfolio.input.UpdatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.DeletePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfoliosResponse;
import com.app.personalfinancesservice.domain.portfolio.output.UpdatePortfolioResponse;
import com.app.personalfinancesservice.service.PortfolioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(HttpRoutes.API_ROOT + HttpRoutes.PORTFOLIO)
@Tag(
		name = "Portfolio Management",
		description = "Endpoint to manage portfolios"
)
public class PortfolioController {

	private final PortfolioService portfolioService;

	public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@Operation(summary = "Create a Portfolio", description = "Create a new portfolio")
	@PostMapping
	public ResponseEntity<CreatePortfolioResponse> createPortfolio(@RequestHeader("X-User-id") String userId, //
			@RequestBody CreatePortfolioRequest request) {

		return ResponseEntity.ok(portfolioService.createPortfolio(request.withUserId(userId)));
	}

	@Operation(summary = "Delete a Portfolio", description = "Delete a portfolio by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<DeletePortfolioResponse> deletePortfolio(@RequestHeader("X-User-id") String userId, @PathVariable String id) {

		DeletePortfolioRequest request = new DeletePortfolioRequest() //
				.withId(id) //
				.withUserId(userId);

		return ResponseEntity.ok(portfolioService.deletePortfolio(request));

	}

	@Operation(summary = "Get a Portfolio", description = "Get a portfolio by its id")
	@GetMapping("/{id}")
	public ResponseEntity<GetPortfolioResponse> getPortfolio(@RequestHeader("X-User-id") String userId, //
			@PathVariable String id) {

		GetPortfolioRequest request = new GetPortfolioRequest() //
				.withPortfolioId(id) //
				.withUserId(userId);

		return ResponseEntity //
				.ok(portfolioService.getPortfolio(request));
	}

	@Operation(summary = "Get a list of Portfolios", description = "Get a list of portfolios by user id")
	@GetMapping("/")
	public ResponseEntity<GetPortfoliosResponse> getPortfolios(@RequestHeader("X-User-id") String userId, //
			@RequestParam(required = false, defaultValue = "CREATED_AT") SortBy sortBy, //
			@RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection) {

		GetPortfoliosRequest request = new GetPortfoliosRequest() //
				.withUserId(userId) //
				.withSortBy(sortBy) //
				.withSortDirection(sortDirection);

		return ResponseEntity //
				.ok(portfolioService.getPortfolios(request));
	}

	@Operation(summary = "Update a Portfolio", description = "Update a portfolio")
	@PutMapping
	public ResponseEntity<UpdatePortfolioResponse> updatePortfolio(@RequestHeader("X-User-id") String userId, //
			@RequestBody UpdatePortfolioRequest request) {

		return ResponseEntity //
				.ok(portfolioService.updatePortfolio(request.withUserId(userId)));
	}
}
