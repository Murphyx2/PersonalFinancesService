package com.app.personalfinancesservice.domain.budget.input;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Budget data for creating")
public class CreateBudgetRequest {

	private String userId;
	private String portfolioId;
	private String name;
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime startAt;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime endAt;

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

	public CreateBudgetRequest withStartAt(LocalDateTime startDate) {
		this.setStartAt(startDate);
		return this;
	}

	public CreateBudgetRequest withEndAt(LocalDateTime endDate) {
		this.setEndAt(endDate);
		return this;
	}
}
