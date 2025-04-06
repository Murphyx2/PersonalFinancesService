package com.app.personalfinancesservice.domain.portfolio.output;

import java.util.List;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPortfolioResponse {

	private List<Portfolio> portfolios;

	public GetPortfolioResponse withPortfolios(List<Portfolio> portfolio) {
		this.setPortfolios(portfolio);
		return this;
	}
}
