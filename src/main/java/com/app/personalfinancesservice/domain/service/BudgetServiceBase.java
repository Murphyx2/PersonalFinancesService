package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.budget.input.CreateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.DeleteBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetRequest;
import com.app.personalfinancesservice.domain.budget.input.GetBudgetsRequest;
import com.app.personalfinancesservice.domain.budget.input.UpdateBudgetRequest;
import com.app.personalfinancesservice.domain.budget.output.CreateBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.DeleteBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetResponse;
import com.app.personalfinancesservice.domain.budget.output.GetBudgetsResponse;
import com.app.personalfinancesservice.domain.budget.output.UpdateBudgetResponse;

public interface BudgetServiceBase {

	CreateBudgetResponse createBudget(CreateBudgetRequest request);

	DeleteBudgetResponse deleteBudget(DeleteBudgetRequest request);

	GetBudgetsResponse getBudgets(GetBudgetsRequest request);

	GetBudgetResponse getBudget(GetBudgetRequest request);

	UpdateBudgetResponse updateBudget(UpdateBudgetRequest request);
}
