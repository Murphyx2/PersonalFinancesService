package com.app.personalfinancesservice.domain.portfolio.output;

import java.util.List;

import com.app.personalfinancesservice.domain.portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPortfolioResponse {

	private List<Portfolio> portfolios;

	public GetAllPortfolioResponse withPortfolios(List<Portfolio> portfolios) {
		this.setPortfolios(portfolios);
		return this;
	}
}
