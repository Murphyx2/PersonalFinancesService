package com.app.personalfinancesservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.domain.budget.Budget;
import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetResponse;
import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.service.BudgetServiceBase;
import com.app.personalfinancesservice.exceptions.PortfolioNotFoundException;
import com.app.personalfinancesservice.repository.BudgetRepository;

@Service
public class BudgetService implements BudgetServiceBase {

	private static final String BUDGET_LABEL = "BUDGET";

	BudgetRepository budgetRepository;

	PortfolioService portfolioService;

	public BudgetService(BudgetRepository budgetRepository, PortfolioService portfolioService) {
		this.budgetRepository = budgetRepository;
		this.portfolioService = portfolioService;
	}

	@Override
	public CreateBudgetResponse createBudget(CreateBudgetRequest request) {

		//Check if portfolio exists
		GetPortfolioRequest requestPortfolio = new GetPortfolioRequest() //
				.withUserId(request.getUserId()) //
				.withPortfolioId(request.getPortfolioId());
		Portfolio portfolio = portfolioService.getPortfolio(requestPortfolio).getPortfolio();

		if (portfolio == null) {
			throw new PortfolioNotFoundException(BUDGET_LABEL, "portfolioId", request.getPortfolioId());
		}

		// Convert request to budget and save it
		Budget requestBudget = BudgetConverter.convert(request);

		return new CreateBudgetResponse() //
				.withBudget(budgetRepository.save(requestBudget));
	}

	/***
	 * It can return one or several budgets
	 *
	 * @param request
	 * @return GetBudgetResponse
	 */
	@Override
	public GetBudgetResponse getBudget(GetBudgetRequest request) {

		final UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", BUDGET_LABEL);
		// fetch old
		if (request.getId() == null) {
			return new GetBudgetResponse().withBudget(budgetRepository.getAllByUserId(userId));
		} else {
			final UUID budgetId = UUIDConverter //
					.convert(request.getId(), "budgetId", BUDGET_LABEL);
			return new GetBudgetResponse().withBudget(budgetRepository.getByIdAndUserId(budgetId, userId));
		}
	}
}
