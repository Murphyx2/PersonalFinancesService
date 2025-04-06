package com.app.personalfinancesservice.domain.service;

import com.app.personalfinancesservice.domain.portfolio.input.CreatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.DeletePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.GetPortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.input.UpdatePortfolioRequest;
import com.app.personalfinancesservice.domain.portfolio.output.CreatePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.DeletePortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.GetPortfolioResponse;
import com.app.personalfinancesservice.domain.portfolio.output.UpdatePortfolioResponse;

public interface PortfolioServiceBase {

	CreatePortfolioResponse createPortfolio(CreatePortfolioRequest request);

	DeletePortfolioResponse deletePortfolio(DeletePortfolioRequest request);

	GetPortfolioResponse getPortfolios(GetPortfolioRequest request);

	UpdatePortfolioResponse updatePortfolio(UpdatePortfolioRequest request);
}
