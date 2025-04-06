package com.app.personalfinancesservice.domain.budget.input;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBudgetRequest {

	private String id;

	private String userId;

	private String name;
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime startAt;

	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
	private LocalDateTime endAt;

	public UpdateBudgetRequest withId(String id) {
		this.setId(id);
		return this;
	}

	public UpdateBudgetRequest withUserId(String userId) {
		this.setUserId(userId);
		return this;
	}

	public UpdateBudgetRequest withName(String name) {
		this.setName(name);
		return this;
	}

	public UpdateBudgetRequest withDescription(String description) {
		this.setDescription(description);
		return this;
	}

	public UpdateBudgetRequest withStartAt(LocalDateTime startAt) {
		this.setStartAt(startAt);
		return this;
	}

	public UpdateBudgetRequest withEndAt(LocalDateTime endAt) {
		this.setEndAt(endAt);
		return this;
	}
}
