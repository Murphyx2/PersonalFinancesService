package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetsResponse;

public interface BudgetServiceBase {

	CreateBudgetResponse createBudget(CreateBudgetRequest request);

	GetBudgetsResponse getBudgets(GetBudgetsRequest request);
}
