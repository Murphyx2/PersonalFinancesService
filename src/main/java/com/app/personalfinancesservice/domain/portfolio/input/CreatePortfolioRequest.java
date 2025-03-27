package com.app.personalfinancesservice.domain.portfolio.input;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortfolioRequest {

	private UUID userId;

	private String name;

	private String description;

	private LocalDateTime created;

	public CreatePortfolioRequest withCreated(LocalDateTime created) {
		this.setCreated(created);
		return this;
	}

	public CreatePortfolioRequest withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public CreatePortfolioRequest withName(String name) {
		this.setName(name);
		return this;
	}

	public CreatePortfolioRequest withUserId(UUID userId) {
		this.setUserId(userId);
		return this;
	}
}
