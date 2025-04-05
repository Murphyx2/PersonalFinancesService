package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.budget.input.UpdateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetsResponse;
import com.app.personalfinancesservice.domain.budget.output.UpdateBudgetResponse;

public interface BudgetServiceBase {

	CreateBudgetResponse createBudget(CreateBudgetRequest request);

	GetBudgetsResponse getBudgets(GetBudgetsRequest request);

	UpdateBudgetResponse updateBudget(UpdateBudgetRequest request);
}
