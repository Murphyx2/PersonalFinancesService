package com.app.personalfinancesservice.domain.portfolio.output;

import java.time.LocalDateTime;
import java.util.UUID;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortfolioResponse {

	private Portfolio portfolio;

	public CreatePortfolioResponse withPortfolio(Portfolio portfolio) {
		this.setPortfolio(portfolio);
		return this;
	}
}
