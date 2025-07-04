package com.app.personalfinancesservice.facade.budget;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.repository.BudgetRepository;
import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.facade.BudgetRepositoryFacade;

@Component
public class BudgetRepositoryFacadeImpl implements BudgetRepositoryFacade {

	private static final String BUDGET_LABEL = "BUDGET";
	private static final String BUDGET_ID_LABEL = "budgetId";
	private static final String USER_ID_LABEL = "userId";

	private final BudgetRepository budgetRepository;

	public BudgetRepositoryFacadeImpl(BudgetRepository budgetRepository) {
		this.budgetRepository = budgetRepository;
	}

	@Override
	public boolean budgetExists(String id, String userId) {

		final UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, BUDGET_LABEL);

		final UUID idUUID = UUIDConverter //
				.convert(id, BUDGET_ID_LABEL, BUDGET_LABEL);

		return budgetRepository.existsByIdAndUserId(idUUID, userIdUUID);
	}

	@Override
	public void deleteBudget(String id, String userId) {

		final UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, BUDGET_LABEL);

		final UUID idUUID = UUIDConverter //
				.convert(id, BUDGET_ID_LABEL, BUDGET_LABEL);

		budgetRepository //
				.getBudgetByIdAndUserId(idUUID, userIdUUID) //
				.ifPresent(budgetRepository::delete);

	}

	@Override
	public Budget getBudgetByIdAndUserId(String id, String userId) {

		final UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, BUDGET_LABEL);

		final UUID idUUID = UUIDConverter //
				.convert(id, BUDGET_ID_LABEL, BUDGET_LABEL);

		return budgetRepository.getBudgetByIdAndUserId(idUUID, userIdUUID) //
				.orElse(null);
	}

	@Override
	public List<Budget> getBudgetsByPortfolioIdAndUserId(String portfolioId, String userId) {

		final UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, BUDGET_LABEL);

		final UUID idUUID = UUIDConverter //
				.convert(portfolioId, "portfolioId", BUDGET_LABEL);

		return budgetRepository.getAllByPortfolioIdAndUserId(idUUID, userIdUUID);
	}

	@Override
	public Budget saveBudget(Budget budget) {

		if (budget == null) {
			throw new MissingIdException(BUDGET_LABEL, "budget");
		}

		return budgetRepository.save(budget);
	}
}
