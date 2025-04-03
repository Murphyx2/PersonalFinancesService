package com.app.personalfinancesservice.domain.budget.input;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBudgetRequest {

	private String userId;
	private String portfolioId;
	private String name;
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime startDate;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime endDate;

	public CreateBudgetRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}

	public CreateBudgetRequest withPortfolioId(String portfolioId) {
		this.setPortfolioId(portfolioId);
		return this;
	}

	public CreateBudgetRequest withName(String name) {
		this.setName(name);
		return this;
	}

	public CreateBudgetRequest withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public CreateBudgetRequest withStartDate(LocalDateTime startDate) {
		this.setStartDate(startDate);
		return this;
	}

	public CreateBudgetRequest withEndDate(LocalDateTime endDate) {
		this.setEndDate(endDate);
		return this;
	}
}
