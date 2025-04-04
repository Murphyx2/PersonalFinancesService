package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetResponse;

public interface BudgetServiceBase {

	CreateBudgetResponse createBudget(CreateBudgetRequest request);

	GetBudgetResponse getBudget(GetBudgetRequest request);
}
