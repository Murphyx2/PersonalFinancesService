package com.app.personalfinancesservice.service;

import java.time.DateTimeException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.converters.BudgetDTOConverter;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.filter.BudgetSorter;
import com.app.personalfinancesservice.utils.DateUtils;
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
import com.personalfinance.api.facade.BudgetRepositoryFacade;
import com.personalfinance.api.facade.PortfolioRepositoryFacade;
import com.personalfinance.api.service.BudgetServiceBase;

@Service
public class BudgetService implements BudgetServiceBase {

	private static final String BUDGET_LABEL = "BUDGET";

	private final BudgetRepositoryFacade budgetRepositoryFacade;
	private final PortfolioRepositoryFacade portfolioRepositoryFacade;

	public BudgetService(BudgetRepositoryFacade budgetRepositoryFacade, PortfolioRepositoryFacade portfolioRepositoryFacade) {
		this.budgetRepositoryFacade = budgetRepositoryFacade;
		this.portfolioRepositoryFacade = portfolioRepositoryFacade;
	}

	@Override
	public CreateBudgetResponse createBudget(CreateBudgetRequest request) {

		// Check if the portfolio exists
		if (!portfolioRepositoryFacade.existsPortfolio(request.getPortfolioId(), request.getUserId())) {
			throw new NotFoundException(BUDGET_LABEL, "portfolio", request.getPortfolioId());
		}

		// Check if dates are correct
		if (DateUtils.isStartDateGreaterThanStartDate(request.getEndAt(), request.getStartAt())) {
			throw new DateTimeException("End date should be greater than start");
		}

		// Convert request to a budget and save it
		BudgetDTO newBudget = budgetRepositoryFacade //
				.saveBudget(BudgetConverter.convert(request));

		return new CreateBudgetResponse().withBudget(newBudget);
	}

	@Override
	public DeleteBudgetResponse deleteBudget(DeleteBudgetRequest request) {

		budgetRepositoryFacade //
				.deleteBudget(request.getId(), request.getUserId());

		return new DeleteBudgetResponse().withSuccess(true);
	}

	@Override
	public GetBudgetResponse getBudget(GetBudgetRequest request) {

		BudgetDTO budget = budgetRepositoryFacade //
				.getBudgetByIdAndUserId(request.getId(), request.getUserId());

		return new GetBudgetResponse().withBudget(budget);
	}

	/***
	 * It can return one or several budgets
	 * @return GetBudgetResponse
	 */
	@Override
	public GetBudgetsResponse getBudgets(GetBudgetsRequest request) {

		// fetch all budgets from Portfolio
		List<BudgetDTO> budgets = budgetRepositoryFacade //
				.getBudgetsByPortfolioIdAndUserId(request.getPortfolioId(), request.getUserId());

		//Filtering results
		List<BudgetDTO> sortedBudgets = BudgetSorter //
				.sort(budgets, request.getSortBy(), request.getSortDirection());

		return new GetBudgetsResponse().withBudgets(sortedBudgets);
	}

	@Override
	public UpdateBudgetResponse updateBudget(UpdateBudgetRequest request) {

		if (request == null) {
			throw new NullPointerException("Update request cannot be empty");
		}

		// Update the oldBudget and save changes
		BudgetDTO updatedBudgetDTO = budgetRepositoryFacade //
				.updateBudget(BudgetDTOConverter.convert(request));

		return new UpdateBudgetResponse().withBudget(updatedBudgetDTO);
	}
}
