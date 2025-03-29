package com.app.personalfinancesservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
	List<Portfolio> getAllByUserId(UUID userId);

	Portfolio getPortfolioByIdAndUserId(UUID id, UUID userId);
}
