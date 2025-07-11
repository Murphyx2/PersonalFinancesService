package com.app.personalfinancesservice.facade.budget;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.app.personalfinancesservice.converters.BudgetConverter;
import com.app.personalfinancesservice.converters.BudgetDTOConverter;
import com.app.personalfinancesservice.converters.UUIDConverter;
import com.app.personalfinancesservice.exceptions.MissingIdException;
import com.app.personalfinancesservice.exceptions.NotFoundException;
import com.app.personalfinancesservice.repository.BudgetRepository;
import com.personalfinance.api.domain.budget.Budget;
import com.personalfinance.api.domain.budget.dto.BudgetDTO;
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
	@CacheEvict(value = "budgets", key = "#userId + '_' + #id")
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
	@Cacheable(value = "budgets", key = "#userId + '_' + #id", unless = "#result == null")
	public BudgetDTO getBudgetByIdAndUserId(String id, String userId) {

		final UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, BUDGET_LABEL);

		final UUID idUUID = UUIDConverter //
				.convert(id, BUDGET_ID_LABEL, BUDGET_LABEL);

		Budget budget = budgetRepository.getBudgetByIdAndUserId(idUUID, userIdUUID) //
				.orElse(null);

		return BudgetDTOConverter.convert(budget);
	}

	@Override
	@Cacheable(value = "budgetsList", key = "#userId + '_' + #portfolioId")
	public List<BudgetDTO> getBudgetsByPortfolioIdAndUserId(String portfolioId, String userId) {

		final UUID userIdUUID = UUIDConverter //
				.convert(userId, USER_ID_LABEL, BUDGET_LABEL);

		final UUID idUUID = UUIDConverter //
				.convert(portfolioId, "portfolioId", BUDGET_LABEL);

		List<Budget> budgets = budgetRepository //
				.getAllByPortfolioIdAndUserId(idUUID, userIdUUID);

		return BudgetDTOConverter.convertMany(budgets);
	}

	@Override
	@CacheEvict(value = "budgetsList", key = "#budget.userId + '_' + #budget.portfolioId")
	public BudgetDTO saveBudget(Budget budget) {

		if (budget == null) {
			throw new MissingIdException(BUDGET_LABEL, "budget");
		}

		return BudgetDTOConverter.convert(budgetRepository.save(budget));
	}

	@Override
	@CacheEvict(value = "budgetsList", key = "#budgetDTO.userId + '_' + #budgetDTO.portfolioId")
	@CachePut(value = "budgets", key = "#budgetDTO.userId + '_' + #budgetDTO.id")
	public BudgetDTO updateBudget(BudgetDTO budgetDTO) {

		if (budgetDTO == null) {
			throw new MissingIdException(BUDGET_LABEL, "budget");
		}

		final UUID userIdUUID = UUIDConverter //
				.convert(budgetDTO.getUserId(), USER_ID_LABEL, BUDGET_LABEL);

		final UUID id = UUIDConverter //
				.convert(budgetDTO.getId(), BUDGET_ID_LABEL, BUDGET_LABEL);

		Budget oldBudget = budgetRepository //
				.getBudgetByIdAndUserId(id, userIdUUID).orElse(null);

		if (oldBudget == null) {
			throw new NotFoundException(BUDGET_LABEL, "budget", budgetDTO.getId());
		}

		Budget updatedBudget = BudgetConverter.convert(budgetDTO, oldBudget);

		return BudgetDTOConverter.convert(budgetRepository.save(updatedBudget));
	}
}
