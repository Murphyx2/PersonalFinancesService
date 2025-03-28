package com.app.personalfinancesservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.personalfinancesservice.domain.Currency.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
}
