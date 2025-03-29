package com.app.personalfinancesservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.personalfinancesservice.domain.budget.Budget;


public interface BudgetRepository extends JpaRepository<Budget, UUID> {
}
