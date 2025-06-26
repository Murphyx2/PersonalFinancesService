package com.app.personalfinancesservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.BudgetSorter;
import com.app.personalfinancesservice.repository.BudgetRepository;
import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.domain.budget.dto.BudgetDTO;
import com.personalfinance.api.domain.budget.input.CreateBudgetRequest;
import com.personalfinance.api.domain.budget.input.DeleteBudgetRequest;
import com.personalfinance.api.domain.budget.input.GetBudgetRequest;
import com.personalfinance.api.domain.budget.input.GetBudgetsRequest;
import com.personalfinance.api.domain.budget.input.UpdateBudgetRequest;
import com.personalfinance.api.domain.budget.output.CreateBudgetResponse;
import com.personalfinance.api.domain.budget.output.DeleteBudgetResponse;
import com.personalfinance.api.domain.budget.output.GetBudgetResponse;
import com.personalfinance.api.domain.budget.output.GetBudgetsResponse;
import com.personalfinance.api.domain.budget.output.UpdateBudgetResponse;
import com.personalfinance.api.domain.portfolio.dto.PortfolioDTO;
import com.personalfinance.api.domain.portfolio.input.GetPortfolioRequest;
import com.personalfinance.api.service.BudgetServiceBase;

@Service
public class BudgetService implements BudgetServiceBase {

	private static final String BUDGET_LABEL = "BUDGET";

	BudgetRepository budgetRepository;

	PortfolioService portfolioService;

	public BudgetService(BudgetRepository budgetRepository, PortfolioService portfolioService) {
		this.budgetRepository = budgetRepository;
		this.portfolioService = portfolioService;
	}

	public boolean budgetExists(UUID budgetId, UUID userId) {
		return budgetRepository.existsByIdAndUserId(budgetId, userId);
	}

	@Override
	public CreateBudgetResponse createBudget(CreateBudgetRequest request) {

		//Check if the portfolio exists
		GetPortfolioRequest requestPortfolio = new GetPortfolioRequest() //
				.withUserId(request.getUserId()) //
				.withPortfolioId(request.getPortfolioId());
		PortfolioDTO portfolio = portfolioService.getPortfolio(requestPortfolio) //
				.getPortfolio();

		if (portfolio == null) {
			throw new NotFoundException(BUDGET_LABEL, "portfolio", request.getPortfolioId());
		}

		// Convert request to budget and save it
		BudgetDTO requestBudget = BudgetConverter.convert(request);

		return new CreateBudgetResponse() //
				.withBudget(budgetRepository.save(requestBudget));
	}

	@Override
	public DeleteBudgetResponse deleteBudget(DeleteBudgetRequest request) {

		GetBudgetRequest requestBudget = new GetBudgetRequest().withId(request.getId()) //
				.withUserId(request.getUserId()) //
				;

		BudgetDTO getResponse = getBudget(requestBudget).getBudget();
		if (getResponse == null) {
			return new DeleteBudgetResponse().withSuccess(false);
		}

		budgetRepository.delete(getResponse);

		return new DeleteBudgetResponse().withSuccess(true);
	}

	@Override
	public GetBudgetResponse getBudget(GetBudgetRequest request) {

		final UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", BUDGET_LABEL);

		final UUID budgetId = UUIDConverter //
				.convert(request.getId(), "budgetId", BUDGET_LABEL);

		Budget budget = budgetRepository.getByIdAndUserId(budgetId, userId) //
				.orElseThrow(() -> new NotFoundException(BUDGET_LABEL, "budget", request.getId()));

		return new GetBudgetResponse().withBudget(budget);
	}

	/***
	 * It can return one or several budgets
	 * @return GetBudgetResponse
	 */
	@Override
	public GetBudgetsResponse getBudgets(GetBudgetsRequest request) {

		List<Budget> budgets;

		final UUID userId = UUIDConverter //
				.convert(request.getUserId(), "userId", BUDGET_LABEL);

		// fetch all budgets from Portfolio
		if (request.getId() == null) {
			final UUID portfolioId = UUIDConverter //
					.convert(request.getPortfolioId(), "portfolioId", BUDGET_LABEL);
			budgets = budgetRepository.getAllByUserIdAndPortfolioId(userId, portfolioId);
		} else {
			final UUID budgetId = UUIDConverter //
					.convert(request.getId(), "budgetId", BUDGET_LABEL);
			budgets = budgetRepository.getListByIdAndUserId(budgetId, userId);
		}

		//Filtering results
		List<Budget> sortedBudgets = BudgetSorter //
				.sort(budgets, request.getSortBy(), request.getSortDirection());

		return new GetBudgetsResponse().withBudgets(sortedBudgets);
	}

	@Override
	public UpdateBudgetResponse updateBudget(UpdateBudgetRequest request) {

		GetBudgetRequest requestBudget = new GetBudgetRequest() //
				.withId(request.getId()) //
				.withUserId(request.getUserId());

		Budget oldBudget = getBudget(requestBudget).getBudget();

		if (oldBudget == null) {
			throw new NotFoundException(BUDGET_LABEL, "budget", request.getId());
		}

		Budget updatedBudget = budgetRepository.save(BudgetConverter.convert(request, oldBudget));

		return new UpdateBudgetResponse().withBudget(updatedBudget);
	}
}
