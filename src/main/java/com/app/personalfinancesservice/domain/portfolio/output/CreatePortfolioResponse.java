package com.app.personalfinancesservice.domain.portfolio.output;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortfolioResponse {

	private UUID id;

	private String name;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime created;

	public CreatePortfolioResponse withCreated(LocalDateTime created) {
		this.setCreated(created);
		return this;
	}

	public CreatePortfolioResponse withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public CreatePortfolioResponse withId(UUID id) {
		setId(id);
		return this;
	}

	public CreatePortfolioResponse withName(String name) {
		this.setName(name);
		return this;
	}
}
