package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalfinance.api.domain.portfolio.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
	List<Portfolio> getAllByUserId(UUID userId);

	Optional<Portfolio> getPortfolioByIdAndUserId(UUID id, UUID userId);
}
