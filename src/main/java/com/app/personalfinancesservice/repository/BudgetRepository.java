package com.app.personalfinancesservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.personalfinancesservice.domain.budget.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
}
